import com.restfb.*;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import com.restfb.FacebookClient;
import com.restfb.types.FacebookType;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;


public class Fotograf {
    OpenCVFrameGrabber grabber;
    BufferedImage foto;
    File resim_dosyasi;

    public Fotograf(){

        grabber = new OpenCVFrameGrabber(0);

        Dimension fotoBoyut = new Dimension(640, 480);
        grabber.setImageWidth(fotoBoyut.width);
        grabber.setImageHeight(fotoBoyut.height);

        try {grabber.start();}
        catch (Exception e) {e.printStackTrace();}

    }

    protected static File dosyaSec(){
        try {
            System.out.println("dosyasec çalıştı!");
            String mevcutdizin=new java.io.File(".").getCanonicalPath();
            JFileChooser ds=new JFileChooser(mevcutdizin);
            ds.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()){return true;}
                    else {
                        String filename = f.getName().toLowerCase();
                        return filename.endsWith(".jpg") ;
                    }
                }

                @Override
                public String getDescription() {
                    return "JPG Resmi (.jpg)";
                }
            });
            ds.showSaveDialog(null);

            System.out.println("dosyasec return");
            return ds.getSelectedFile();
        }
        catch (Exception e) {e.printStackTrace();}
        return null;
    }


    public void canli(JLabel imgLabel){
        try {
            Java2DFrameConverter converter = new Java2DFrameConverter();
            foto = converter.convert(grabber.grab());

            imgLabel.setIcon(new ImageIcon(foto));

            Thread.sleep(33);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void fotografCek(){


    }

    public void fotografKaydet(){
        File dosya=new File(dosyaSec().toString()+".jpg");
        resim_dosyasi=dosya;
        try {
            ImageIO.write(foto, "jpg", dosya);
            JOptionPane.showMessageDialog(null, "Kaydedildi", "kayit", JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e){e.printStackTrace();}
    }

    public void fotoPaylas(){

        //30 temmuza kadar geçerli
        String accessToken = "EAAT7Kw6YKxkBAEfY9F4iC7icFFeX3ZA5ysohwpZAo13uHod14Puk8UOQwG9d0AkE6ZCo9gkO3U93ejK8WeKIsZBvKX7gX8qpXS7ScH7ZCZA3mke9sxWFFiQA7QewgdUuNsgNaV3dff6BZByp16J6Bwq0iGNePofQuiaEKblNmFTd7KcHJYkEa5REn2S3ombUXMZD";
        //
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);

        // Paylaşmak istediğiniz fotoğrafın yolu
        String photoPath = resim_dosyasi.toString(); // "C:\\Users\\Beytullah\\res3.jpg";

        try {
            FileInputStream fis = new FileInputStream(photoPath);
            if (fis != null) {
                // Fotoğrafı yükleyin
                FacebookType response = facebookClient.publish("/100093235084656/photos", FacebookType.class,
                        BinaryAttachment.with("resim",fis),
                        Parameter.with("message", "deneme"));

                // Yüklenen fotoğrafın ID'sini alın
                String photoId = response.getId();

                String yuklemeMesaj="Fotoğraf başarıyla yüklendi. ID: " + photoId.toString();
                JOptionPane.showMessageDialog(null, yuklemeMesaj, "Kayıt", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Fotoğrafın veri içeriği eksik!");
            }
        }catch (Exception e){e.printStackTrace();}


    }
}
