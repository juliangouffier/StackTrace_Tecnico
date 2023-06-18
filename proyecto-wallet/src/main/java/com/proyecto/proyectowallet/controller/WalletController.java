package com.proyecto.proyectowallet.controller;

import com.proyecto.proyectowallet.model.*;
import com.proyecto.proyectowallet.services.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class WalletController {
    private final Logger LOGGER = LoggerFactory.getLogger(WalletEntity.class);

    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;

    @Autowired
    private TiposCriptoService tiposCriptoService;
    @Autowired
    private CriptoDivisasService criptoDivisasService;

    @Autowired
    private TransaccionesService transaccionesService;

    @GetMapping("/home")
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findByEmail(userDetails.getUsername());

        List<WalletEntity> billeteras = walletService.findByIDAll(user.getId());

        for(WalletEntity ab : billeteras)
        {
            //imprimimos el objeto pivote
            LOGGER.info("Billeteras {}",ab.getHash());

        }


        model.addAttribute("billeteras",billeteras);
        return "home";
    }
    @PostMapping("/home/createWallet")
    public ResponseEntity<String> createWallet(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userService.findByEmail(userDetails.getUsername());
        WalletEntity wallet = new WalletEntity();

        wallet.setUsuario(user);

        //wallet.setCriptos(null);
        wallet.setHash(walletService.createHash(user.getEmail()));

        List<TiposCriptoEntity> divisas = tiposCriptoService.findAll();
        String retorno =  walletService.guardar(wallet,divisas);
        if(retorno.equals("1")){
            //devolver pagina web exito
            return ResponseEntity.ok("Se creo la billetera con exito");
        }else{
            //devolver pagina web fracaso
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido crear una nueva billetera");
        }



    }
    @GetMapping("home/depositarCripto/{id}")
    public String depositarCripto(@PathVariable Long id, Model model){
        List<TiposCriptoEntity> criptos = tiposCriptoService.findAll();
        float valor = 0;
        model.addAttribute("criptos", criptos);

        model.addAttribute("deposito",new CriptoDivisasEntity());

        model.addAttribute("biletera_id", id);

        LOGGER.info(id.toString());

        return "depositarCripto";
    }

    @PostMapping("/home/depositarCripto/{id}")
    public String addCripto(@PathVariable Long id,@ModelAttribute("deposito") CriptoDivisasEntity criptoDivisasEntity) {

     try{
         CriptoDivisasEntity vieja = criptoDivisasService.findByNombreDivisaAndBilletera(id,criptoDivisasEntity.getNombre_divisa());
         float calculo = vieja.getCantidad_divisa() + criptoDivisasEntity.getCantidad_divisa();
         vieja.setCantidad_divisa(calculo);
         WalletEntity walletActual = walletService.getWallet(id);
         LocalDate fecha = LocalDate.now();
         LocalTime hora = LocalTime.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
         String formattedTime = hora.format(formatter);
         TransaccionesEntity transaccionesEntity = new TransaccionesEntity("-",walletActual.getHash().toString(),"DEPOSITO",criptoDivisasEntity.getNombre_divisa(),criptoDivisasEntity.getCantidad_divisa(),fecha,formattedTime);
         transaccionesService.guardarTransaccion(transaccionesEntity);
         criptoDivisasService.update(vieja);
     }catch (Exception e){
         e.printStackTrace();
     }


        return "redirect:/home";
    }

    @GetMapping("/home/verBilletera/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("billetera", walletService.getWallet(id));
        float calculo=0;

        List<CriptoDivisasEntity> a = criptoDivisasService.getAllDivisasFromWallet(id);
        if(!a.isEmpty()){
            for(int i=0; i<a.size();i++){
                float multi = a.get(i).getCantidad_divisa() * a.get(i).getValor_divisa();
                calculo = calculo + multi;
            }

        }
        model.addAttribute("total",calculo);
        model.addAttribute("criptos",a);

        LOGGER.info(a.toString());
        return "verBilletera";
    }


    @GetMapping("/home/verBilletera/transferir/{id}")
    public String transferir(@PathVariable Long id, Model model){
        model.addAttribute("billetera", walletService.getWallet(id));
        model.addAttribute("id",id);
        long billeteraDest =0;
        String criptodivisa = "";
        float cantidad = 0;
        model.addAttribute("billeteraDest",billeteraDest);
        model.addAttribute("criptodivisa",criptodivisa);
        List<CriptoDivisasEntity> criptos = criptoDivisasService.getAllDivisasFromWallet(id);
        model.addAttribute("criptos",criptos);
        return "transferirCripto";
    }

    @PostMapping("/home/verBilletera/transferir/{id}")
    public String transferirCripto(@PathVariable Long id, @RequestParam("billeteraDest") long billeteraDest, @RequestParam("criptodivisa") String criptodivisa, @RequestParam("cantidad") float cantidad){

   try{

       WalletEntity walletOrigen = walletService.getWallet(id);
       WalletEntity walletDestino = walletService.buscarWalletPorHash(billeteraDest);
       CriptoDivisasEntity criptosOrigen = criptoDivisasService.findByNombreDivisaAndBilletera(walletOrigen.getId(),criptodivisa);
       CriptoDivisasEntity criptosDestino = criptoDivisasService.findByNombreDivisaAndBilletera(walletDestino.getId(),criptodivisa);


        float origenCant = criptosOrigen.getCantidad_divisa();
        float destinoCant = criptosDestino.getCantidad_divisa();
        float origenCantFinal = 0;
        float destinoCantFinal = 0;

        if(cantidad<=origenCant && !walletOrigen.getHash().equals(walletDestino.getHash())){ // habilitado a hacer la transferencia
            origenCantFinal = origenCant - cantidad;
            destinoCantFinal = destinoCant + cantidad;

            criptosOrigen.setCantidad_divisa(origenCantFinal);
            criptosDestino.setCantidad_divisa(destinoCantFinal);
            criptoDivisasService.update(criptosOrigen);
            criptoDivisasService.update(criptosDestino);

            LocalDate fecha = LocalDate.now();
            LocalTime hora = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
            String formattedTime = hora.format(formatter);
            TransaccionesEntity nueva = new TransaccionesEntity(walletOrigen.getHash().toString(),walletDestino.getHash().toString(),"TRANSFERENCIA",criptodivisa,cantidad,fecha,formattedTime);
            transaccionesService.guardarTransaccion(nueva);
        }






   }catch (Exception e){
       e.printStackTrace();
   }




        return "redirect:/home/verBilletera/{id}";
    }

    @GetMapping("/home/verBilletera/intercambiar/{id}")
    public String intercambiarCripto(@PathVariable Long id, Model model){
        WalletEntity wallet = walletService.getWallet(id);
        float cantidad=0;
        String nombre_origen="";
        String nombre_destino="";
        model.addAttribute("cantidad",cantidad);
        model.addAttribute("nombre_origen",nombre_origen);
        model.addAttribute("nombre_destino",nombre_destino);
        model.addAttribute("billetera",wallet);
        model.addAttribute("criptos", criptoDivisasService.getAllDivisasFromWallet(wallet.getId()));
        return "intercambiarCripto";


    }
    @PostMapping("/home/verBilletera/intercambiar/{id}")
    public String intercambiar(@PathVariable Long id, @RequestParam("cantidad") float cantidad, @RequestParam("nombre_origen") String nombre_origen, @RequestParam("nombre_destino") String nombre_destino){

        try{
            CriptoDivisasEntity origen = criptoDivisasService.findByNombreDivisaAndBilletera(id,nombre_origen);
            CriptoDivisasEntity destino = criptoDivisasService.findByNombreDivisaAndBilletera(id,nombre_destino);
            if(!nombre_destino.equals(nombre_origen)){
                if(cantidad > 0 && cantidad <= origen.getCantidad_divisa()){
                    float cantOrigenEnPesos = (cantidad * origen.getValor_divisa());
                    float result = cantOrigenEnPesos / destino.getValor_divisa();
                    origen.setCantidad_divisa(origen.getCantidad_divisa()-cantidad);
                    destino.setCantidad_divisa(result+destino.getCantidad_divisa());
                    criptoDivisasService.update(origen);
                    criptoDivisasService.update(destino);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }



        return "redirect:/home/verBilletera/{id}";

    }
}
