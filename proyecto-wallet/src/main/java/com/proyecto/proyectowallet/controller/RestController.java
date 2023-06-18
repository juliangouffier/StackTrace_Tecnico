package com.proyecto.proyectowallet.controller;


import com.proyecto.proyectowallet.dto.SaldoBilleterasDto;
import com.proyecto.proyectowallet.dto.SaldoTotalBilleterasDto;
import com.proyecto.proyectowallet.dto.TransferenciaDto;
import com.proyecto.proyectowallet.model.*;
import com.proyecto.proyectowallet.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private TiposCriptoService tiposCriptoService;

    @Autowired
    private TransaccionesService transaccionesService;
    @Autowired
    private CriptoDivisasService criptoDivisasService;


    @PostMapping("/api/createUser")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user){
        UserEntity user1 = userService.saveUser(user);

        return ResponseEntity.ok(user1);
    }

    @PutMapping("/api/editUser/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("id")Long id, @RequestBody UserEntity userRecibido){

        UserEntity user = userService.findById(id).get();
        if(user!=null){
            user.setNombre(userRecibido.getNombre());
            user.setPassword(userRecibido.getPassword());
            user.setEmail(userRecibido.getEmail());
            user.setApellido(userRecibido.getApellido());
            user.setSexo(userRecibido.getSexo());
            user.setDni(userRecibido.getDni());
            user.setTelefono(userRecibido.getTelefono());

            userService.modificarUsuario(user);
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/api/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        try{
            List<WalletEntity> wallets = walletService.findByIDAll(id);
            if(!wallets.isEmpty()) {
                List<Long> ids = new ArrayList<>();
                //borrar criptodivisas primero
                for (int i = 0; i < wallets.size(); i++) {
                    ids.add(wallets.get(i).getId());

                }
                criptoDivisasService.eliminarArctivosPorBilletera(ids);
                userService.deleteUser(id);
                return ResponseEntity.ok("Se elimino");
            }else{
                userService.deleteUser(id);
                return ResponseEntity.ok("Se elimino");
            }
        }catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar el usuario");

        }


    }

    @GetMapping("/api/getUsers")
    public List<UserEntity> getUsers(){

            return this.userService.findAll();
    }


    @PostMapping("/api/altaDivisa")
    public ResponseEntity<TiposCriptoEntity> altaDivisa(@RequestBody TiposCriptoEntity divisa){

            List<WalletEntity> billeteras = walletService.getAllWallets();
            tiposCriptoService.save(divisa,billeteras);
            return ResponseEntity.ok(divisa);
    }

    @DeleteMapping("/api/deleteDivisa/{id}")
    public ResponseEntity<String> deleteDivisa(@PathVariable Long id){

        try{
            List<CriptoDivisasEntity> filas = criptoDivisasService.buscarNombre(tiposCriptoService.findById(id).get().getNombre_cripto());

            criptoDivisasService.eliminarFilas(filas);
            tiposCriptoService.delete(id);
            return ResponseEntity.ok("Se realizo la baja");
        }catch (Exception e){
            return ResponseEntity.ok("No se pudo realizar la baja");
        }

    }

    @PutMapping("/api/modificarDivisa/{id}")
    public ResponseEntity<TiposCriptoEntity> modificarDivisa(@PathVariable Long id, @RequestBody TiposCriptoEntity divisa){

        String nombre = tiposCriptoService.update(divisa,id);
        if(!nombre.equals("null")){
            List<CriptoDivisasEntity>filas= criptoDivisasService.buscarNombre(nombre);
            criptoDivisasService.actualizarValores(divisa,filas);
            return ResponseEntity.ok(divisa);
        }
        return (ResponseEntity<TiposCriptoEntity>) ResponseEntity.notFound();

    }
    @GetMapping("/api/listarDivisas/")
    public List<TiposCriptoEntity> listarDivisas(){

        return tiposCriptoService.findAll();
    }
    @PostMapping("/api/createWallet/{id}") //pido id del usuario al cual le voy a asignar la billetera
    public ResponseEntity<WalletEntity> crearWallet(@PathVariable Long id){

        UserEntity user = userService.findById(id).orElse(null);
        if(user!=null){
            WalletEntity wallet = new WalletEntity();

            Long hash = walletService.createHash(user.getEmail());
            wallet.setHash(hash);
            wallet.setUsuario(user);
            List<TiposCriptoEntity> listaDivisas = tiposCriptoService.findAll();
            walletService.guardar(wallet,listaDivisas);
            return ResponseEntity.ok(wallet);
        }
        return (ResponseEntity<WalletEntity>) ResponseEntity.notFound();
    }

    @PutMapping("/api/modificarWallet/{id}")
    public ResponseEntity<WalletEntity> modificarWallet(@PathVariable Long id, @RequestBody WalletEntity walletHash){

        WalletEntity wallet = walletService.getWallet(id);
        wallet.setHash(walletHash.getHash());
        walletService.update(wallet);


        return ResponseEntity.ok(wallet);
    }

    @DeleteMapping("/api/deleteWallet/{id}")
    public ResponseEntity<String> deleteWallet(@PathVariable Long id){
       WalletEntity wallet = walletService.getWallet(id);
        if(wallet!=null){
            List<Long> lista = new ArrayList<>();
            lista.add(id);
            criptoDivisasService.eliminarArctivosPorBilletera(lista);
            walletService.deleteWallet(wallet);
            return ResponseEntity.ok("Se elimino correctamente");
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }


    @PutMapping("/api/transferir/")
    public ResponseEntity<TransferenciaDto> transferencia(@RequestBody TransferenciaDto transferenciaDto){
        WalletEntity walletOrigen = walletService.buscarWalletPorHash(transferenciaDto.getWalletOrigen());
        WalletEntity walletDestino = walletService.buscarWalletPorHash(transferenciaDto.getWalletDestino());
        CriptoDivisasEntity criptosOrigen = criptoDivisasService.findByNombreDivisaAndBilletera(walletOrigen.getId(),transferenciaDto.getCriptoDivisa());
        CriptoDivisasEntity criptosDestino = criptoDivisasService.findByNombreDivisaAndBilletera(walletDestino.getId(),transferenciaDto.getCriptoDivisa());

        if(walletOrigen!=null && walletDestino!=null){
            if(criptosOrigen!=null && criptosDestino!=null)
            {
                if(transferenciaDto.getCantidad()<=criptosOrigen.getCantidad_divisa())
                {
                    float origenCant = criptosOrigen.getCantidad_divisa();
                    float destinoCant = criptosDestino.getCantidad_divisa();
                    float origenCantFinal = 0;
                    float destinoCantFinal = 0;
                    origenCantFinal = origenCant - transferenciaDto.getCantidad();
                    destinoCantFinal = destinoCant + transferenciaDto.getCantidad();

                    criptosOrigen.setCantidad_divisa(origenCantFinal);
                    criptosDestino.setCantidad_divisa(destinoCantFinal);
                    criptoDivisasService.update(criptosOrigen);
                    criptoDivisasService.update(criptosDestino);

                    LocalDate fecha = LocalDate.now();
                    LocalTime hora = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
                    String formattedTime = hora.format(formatter);
                    TransaccionesEntity nueva = new TransaccionesEntity(walletOrigen.getHash().toString(),walletDestino.getHash().toString(),"TRANSFERENCIA",transferenciaDto.getCriptoDivisa(),transferenciaDto.getCantidad(),fecha,formattedTime);
                    transaccionesService.guardarTransaccion(nueva);
                    transferenciaDto.setEstado("Transferido");
                    return ResponseEntity.ok(transferenciaDto);
                }else{
                    transferenciaDto.setEstado("NO TRANSFERIDA!");
                    return ResponseEntity.ok(transferenciaDto);
                }

            }else {
                transferenciaDto.setEstado("NO TRANSFERIDA!");
                return ResponseEntity.ok(transferenciaDto);
            }

        }else {
            transferenciaDto.setEstado("NO TRANSFERIDA!");
            return ResponseEntity.ok(transferenciaDto);
        }



    }

    @PutMapping("/api/deposito/{id}")
    public ResponseEntity<CriptoDivisasEntity> depositar(@PathVariable Long id, @RequestBody CriptoDivisasEntity criptoDivisasEntity){
        CriptoDivisasEntity vieja = criptoDivisasService.findByNombreDivisaAndBilletera(id,criptoDivisasEntity.getNombre_divisa());
        if(vieja!=null){
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
            return ResponseEntity.ok(vieja);
        }else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @GetMapping("/api/consultarSaldo/{id}")
    public ResponseEntity<SaldoBilleterasDto> consultarSaldo(@PathVariable Long id){
        List<CriptoDivisasEntity> criptos = criptoDivisasService.getAllDivisasFromWallet(id);
        if(!criptos.isEmpty()){
            SaldoBilleterasDto saldo = new SaldoBilleterasDto();
            float calculo=0;
            float total=0;
            for(int i=0; i<criptos.size(); i++){
                calculo = criptos.get(i).getValor_divisa() * criptos.get(i).getCantidad_divisa();
                total = calculo + total;
            }
            saldo.setTotal(total);
            saldo.setCriptomonedas(criptos);
            saldo.setId(id);
            return ResponseEntity.ok(saldo);
        }

        SaldoBilleterasDto saldo = new SaldoBilleterasDto();

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/consultarSaldoDeUsuario/{id}")
    public ResponseEntity<SaldoTotalBilleterasDto> consultarSaldoDeUsuarioTotal(@PathVariable Long id){
        List<WalletEntity> wallets = walletService.findByIDAll(id);
        if(!wallets.isEmpty()){
            List<List<CriptoDivisasEntity>> listaCriptos = new ArrayList<>();
            float total = 0;
            float calculo=0;
            for(int i =0; i<wallets.size(); i++){
                List<CriptoDivisasEntity> criptos = criptoDivisasService.getAllDivisasFromWallet(wallets.get(i).getId());
                listaCriptos.add(criptos);
                for(int j=0; j<criptos.size();j++){
                    calculo = criptos.get(j).getCantidad_divisa() * criptos.get(j).getValor_divisa();
                    total = calculo + total;
                }

            }
            SaldoTotalBilleterasDto saldo = new SaldoTotalBilleterasDto();
            saldo.setTotal(total);
            saldo.setUsuario_id(id);
            saldo.setCriptomonedas(listaCriptos);
            saldo.setBilleteras(wallets);
            return ResponseEntity.ok(saldo);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }





}
