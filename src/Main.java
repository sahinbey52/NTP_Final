import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import java.util.Scanner;


public class Main extends JFrame{

    public boolean cek;
    public static void main(String[] args) {
        Main mn =new Main();
        mn.cek=true;

        JFrame frm=new JFrame();
        JPanel panel = new JPanel(new GridLayout(2,2));
        JLabel img=new JLabel();
        panel.add(img);

        JButton btn=new JButton("Çek");
        panel.add(btn);
        JButton btnkaydet =new JButton("Kaydet");
        panel.add(btnkaydet);
        JButton btntw=new JButton("Twitter");
        panel.add(btntw);
        JButton btnsb=new JButton("SB yap");
        panel.add(btnsb);
        JButton btnparlat=new JButton("parlat");
        panel.add(btnparlat);
        JButton btnkontr=new JButton("Kontrast");
        panel.add(btnkontr);
        JButton btnblnk=new JButton("Bulanık");
        panel.add(btnblnk);

        /*JCheckBox cb_sb=new JCheckBox("Siyah-beyaz");
        JCheckBox cb_parlat=new JCheckBox("Parlat");
        JCheckBox cb_kontr=new JCheckBox("Kontrast");
        JCheckBox cb_blnk=new JCheckBox("Bulanıklaştır");
        panel.add(cb_sb);
        panel.add(cb_parlat);
        panel.add(cb_kontr);
        panel.add(cb_blnk);*/


        frm.getContentPane().add(panel);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(640, 480);

        frm.setVisible(true);

        Filtre ft=new Filtre();

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mn.cek=false;
                ft.fotografCek();
            }
        });

        btnkaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("butona tıklandı");
                ft.fotografKaydet();

            }
        });
        btntw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ft.fotoPaylas();
            }
        });
        btnsb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ft.siyah_beyaz(img);

            }
        });
        btnparlat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ft.parlat(img);

            }
        });
        btnkontr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ft.kontrast(img);

            }
        });
        btnblnk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ft.bulanik(img);

            }
        });

        /*cb_sb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Siyah beyaz yapıldı");
            }
        });
        cb_kontr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Kontrast ayarlandı");
            }
        });
        cb_parlat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Parlatıldı");
            }
        });
        cb_blnk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Bulanıklaştırıldı");
            }
        });*/

        //FacebookClient fbc=new DefaultFacebookClient();

        while (mn.cek){
            ft.canli(img);
        }


    }
}