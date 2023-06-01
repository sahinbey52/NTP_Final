import com.restfb.*;

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
    static BufferedImage foto;
    static File resim_dosyasi;


    /*Dosya kaydet diyalog penceresi açarak dosya sisteminde seçilen dosya yolunu File olarak döndürür.*/
    protected static File dosyaSec(){
        try {
            /*O anki dzinin alınması ve varsayılan olarak ayarlanması*/
            String mevcutdizin=new java.io.File(".").getCanonicalPath();
            JFileChooser ds=new JFileChooser(mevcutdizin);

            /*Dosya filtresinin ayarlanması*/
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

            /*Diyalog penceresinin açılması*/
            ds.showSaveDialog(null);

            /*Dosya yolunun döndürülmesi*/
            return ds.getSelectedFile();
        }
        catch (Exception e) {e.printStackTrace();}
        return null;
    }

    /*dosyaSec() fonksiyonunu çağırarak dosya sisteminden dosya yolu seçimini sağlayıp foto değikenindeki
    resim dosyasını belirtilen dosya yoluna kaydeder*/
    static public void fotografKaydet(){
        /*Dosya yolunun seçilmesi*/
        File dosya=new File(dosyaSec().toString()+".jpg");
        resim_dosyasi=dosya;
        try {
            /*Dosyanın kaydedilmesi*/
            ImageIO.write(foto, "jpg", dosya);
            JOptionPane.showMessageDialog(null, "Kaydedildi", "Kayıt", JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e){e.printStackTrace();}
    }

    /*Daha önce ayarlanmış olan Facebook sayfasına foto değişkenindeki fotoğrafı yükler*/
    static public void fotoPaylas(){

        /*Fotoğraf yükleme için alınan sayfa erişim belirteci*/
        String accessToken = "EAAT7Kw6YKxkBAEfY9F4iC7icFFeX3ZA5ysohwpZAo13uHod14Puk8UOQwG9d0AkE6ZCo9gkO3U93ejK8WeKIsZBvKX7gX8qpXS7ScH7ZCZA3mke9sxWFFiQA7QewgdUuNsgNaV3dff6BZByp16J6Bwq0iGNePofQuiaEKblNmFTd7KcHJYkEa5REn2S3ombUXMZD";

        /*İstemci oluşturma*/
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);

        /*Dosya yolunun ayarlanması*/
        String photoPath = resim_dosyasi.toString();

        try {
            FileInputStream fis = new FileInputStream(photoPath);
            if (fis != null) {
                /*Fotoğrafın yüklenmesi*/
                FacebookType response = facebookClient.publish("/100093235084656/photos", FacebookType.class,
                        BinaryAttachment.with("resim",fis),
                        Parameter.with("message", "Fotoğraf paylaşımı"));

                /*Yüklenen fotoğrafın ID'sini alma*/
                String photoId = response.getId();

                String yuklemeMesaj="Fotoğraf başarıyla yüklendi. ID: " + photoId.toString();
                JOptionPane.showMessageDialog(null, yuklemeMesaj, "Kayıt", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Fotoğrafın veri içeriği eksik!");
            }
        }catch (Exception e){e.printStackTrace();}

    }

    /*BufferedImage türünde değişken alıp onun içeriğini yeni bir BufferedImage değişkeninde döndürür*/
    protected static BufferedImage fotoKopyala(BufferedImage orj) {
        BufferedImage kopyalananResim = new BufferedImage(orj.getWidth(), orj.getHeight(), orj.getType());
        Graphics2D g = kopyalananResim.createGraphics();
        g.drawImage(orj, 0, 0, null);
        g.dispose();
        return kopyalananResim;
    }

}
