import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.conf.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


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

        //try{grabber.stop();}catch(Exception e){}

    }

    public void fotografKaydet(){
        File dosya=new File(dosyaSec().toString()+".jpg");
        resim_dosyasi=dosya;
        try {
            ImageIO.write(foto, "jpg", dosya);
            JOptionPane.showMessageDialog(null, "Kaydedildi", "Kayıt", JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e){e.printStackTrace();}
    }

    public void fotoPaylas(){

        try {
            String consumerKey = "5CDsM7IM3J7MZJWskdZUNX10u";
            String consumerSecret = "cgvxOK5UhceIYta1PaHi5XUD3UMSKYCkr0ammIgEhHfiqUBQgF";
            String accessToken = "1660519623274516480-JDMerzAp46NWur8A0mtelvqqnMm2Ru";
            String accessTokenSecret = "uFlEuVyzQLhp6ixPn8ix69RbLujnlIfqcKt8m1sEkS6B6";

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(consumerKey)
                    .setOAuthConsumerSecret(consumerSecret)
                    .setOAuthAccessToken(accessToken)
                    .setOAuthAccessTokenSecret(accessTokenSecret);

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            StatusUpdate tweet = new StatusUpdate("Deneme");
            tweet.setMedia(resim_dosyasi);
            Status st = twitter.updateStatus(tweet);
            JOptionPane.showMessageDialog(null, "Yüklendi", "tweet", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {e.printStackTrace();}

    }
}
