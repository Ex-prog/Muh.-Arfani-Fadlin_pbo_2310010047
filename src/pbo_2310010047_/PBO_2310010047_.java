/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pbo_2310010047_;
import crud.crudadmin;
import crud.crudjenisikan;
import crud.crudpembeli;
import crud.crudpenjual;
import crud.crudpesanan;
import frame.frameadmin;
import frame.framejenisikan;
import frame.framepembeli;
import frame.framepenjual;
import frame.framepesanan;


/**
 *
 * @author USER
 */
public class PBO_2310010047_ {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       new frameadmin().setVisible(true);
       new framejenisikan().setVisible(true);
       new framepembeli().setVisible(true);
       new framepenjual().setVisible(true);
       new framepesanan().setVisible(true);
    }
    
}
