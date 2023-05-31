import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacv.*;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_imgproc.*;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static org.bytedeco.opencv.global.opencv_core.CV_8UC3;
import static org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur;


public class Filtre extends Fotograf {

    BufferedImage filtrefoto;

    public Filtre(){
        super();
    }

    @Override
    public void fotografCek(){
        filtrefoto=fotoKopyala(foto);
    }

    @Override
    public void fotografKaydet(){
        File dosya=new File(dosyaSec().toString()+".jpg");
        resim_dosyasi=dosya;
        try {
            ImageIO.write(filtrefoto, "jpg", dosya);
            JOptionPane.showMessageDialog(null, "Kaydedildi", "kayit", JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e){e.printStackTrace();}
    }

    public void siyah_beyaz(JLabel imgLabel){

        filtrefoto=siyah_beyazUygula(filtrefoto);
        imgLabel.setIcon(new ImageIcon(filtrefoto));
    }

    public void parlat(JLabel imgLabel){
        filtrefoto=parlaklikUygula(filtrefoto);
        imgLabel.setIcon(new ImageIcon(filtrefoto));
    }

    public void kontrast(JLabel imgLabel){
        filtrefoto=kontrastUygula(filtrefoto);
        imgLabel.setIcon(new ImageIcon(filtrefoto));
    }

    public void bulanik(JLabel imgLabel){
        filtrefoto=bulaniklastir(filtrefoto);
        imgLabel.setIcon(new ImageIcon(filtrefoto));
    }

    public void ftemizle(JLabel imgLabel){
        filtrefoto=fotoKopyala(foto);
        imgLabel.setIcon(new ImageIcon(filtrefoto));
    }


    private static BufferedImage siyah_beyazUygula(BufferedImage img){
        //Fotoğraf Mat nesnesine dönüştürülür
        Mat mat =new Mat(img.getHeight(), img.getWidth(), CV_8UC3);
        byte[] pikseller = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        mat.data().put(pikseller);

        //Siyah-beyaz filtre uygulanır
        Mat siyah_beyaz=new Mat();
        opencv_imgproc.cvtColor(mat, siyah_beyaz, opencv_imgproc.COLOR_BGR2GRAY);

        //Mat, BufferedImage'a dönüştürülerek döndürülür
        return Java2DFrameUtils.toBufferedImage(siyah_beyaz);
        /*BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = image.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return image;*/
    }

    private static BufferedImage parlaklikUygula(BufferedImage img){
        //Fotoğraf Mat nesnesine dönüştürülür
        Mat mat =new Mat(img.getHeight(), img.getWidth(), CV_8UC3);
        byte[] pikseller = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        mat.data().put(pikseller);

        //Parlaklık filtresi uygulanır
        Mat parlak=new Mat();
        mat.convertTo(parlak, -1, 1.5, 0); //Parlaklık katsayısı 1.5

        //Mat, BufferedImage'a dönüştürülerek döndürülür
        return Java2DFrameUtils.toBufferedImage(parlak);
    }

    private static BufferedImage kontrastUygula(BufferedImage img){
        //Fotoğraf Mat nesnesine dönüştürülür
        Mat mat =new Mat(img.getHeight(), img.getWidth(), CV_8UC3);
        byte[] pikseller = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        mat.data().put(pikseller);

        //Kontrast filtresi uygulanır
        Mat kontrast=new Mat();
        mat.convertTo(kontrast, -1, 1.0, 50); //Kontrast katsayısı 1.0

        //Mat, BufferedImage'a dönüştürülerek döndürülür
        return Java2DFrameUtils.toBufferedImage(kontrast);
    }

    private static BufferedImage bulaniklastir(BufferedImage img){
        //Fotoğraf Mat nesnesine dönüştürülür
        Mat mat =new Mat(img.getHeight(), img.getWidth(), CV_8UC3);
        byte[] pikseller = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        mat.data().put(pikseller);

        //Kontrast filtresi uygulanır
        Mat bulanik=new Mat();
        GaussianBlur(mat,bulanik,new Size(3,3), 0);

        //Mat, BufferedImage'a dönüştürülerek döndürülür
        return Java2DFrameUtils.toBufferedImage(bulanik);
    }

    private static BufferedImage fotoKopyala(BufferedImage orj) {
        BufferedImage kopyalananResim = new BufferedImage(orj.getWidth(), orj.getHeight(), orj.getType());
        Graphics2D g = kopyalananResim.createGraphics();
        g.drawImage(orj, 0, 0, null);
        g.dispose();
        return kopyalananResim;
    }
}
