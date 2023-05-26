import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;



public class Main {

    static OpenCVFrameGrabber grabber;
    BufferedImage foto;
    File resim_dosyasi;

    public static void main(String[] args) {
        // Kamera bağlantısını oluştur
        grabber = new OpenCVFrameGrabber(0);

        Dimension fotoBoyut = new Dimension(640, 480);
        grabber.setImageWidth(fotoBoyut.width);
        grabber.setImageHeight(fotoBoyut.height);

        try {grabber.start();}
        catch (Exception e) {e.printStackTrace();}

        // CanvasFrame oluşturma
        CanvasFrame fram = new CanvasFrame("Kamera Görüntüsü");

        // JFrame ve JLabel oluşturma
        JFrame frame = new JFrame("Fotoğraf Uygulaması");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Tam ekran modunda başlatma
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());


        //Sol üstteki fotoğraf bölümü
        if (fram.isVisible()) {
            // Kameradan bir frame al
            try {
                Frame capturedFrame = grabber.grab();
                // Frame'i ekrana göster
                fram.showImage(capturedFrame);
                JLabel imageLabel = new JLabel(new ImageIcon("resim.jpg"));
                frame.add(imageLabel, BorderLayout.WEST);
            }
            catch (Exception e) {e.printStackTrace();
            }
            // Sağdaki butonlar bölümü
            JPanel buttonsPanel = new JPanel(new GridLayout(3, 1));
            JButton aydinlatButton = new JButton("Aydınlat");
            buttonsPanel.add(aydinlatButton);

            JButton parlatButton = new JButton("Parlat");
            buttonsPanel.add(parlatButton);

            JButton grilestirButton = new JButton("Grileştir");
            buttonsPanel.add(grilestirButton);

            frame.add(buttonsPanel, BorderLayout.CENTER);

        aydinlatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aydınlat butonuna tıklandığında yapılacak işlemler
            }
        });

        parlatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Parlat butonuna tıklandığında yapılacak işlemler
            }
        });

        grilestirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Grileştir butonuna tıklandığında yapılacak işlemler
            }
        });

        // Altta bulunan butonlar bölümü
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton kaydetButton = new JButton("Kaydet");
        bottomPanel.add(kaydetButton);

        JButton durdurButton = new JButton("Durdur");
        bottomPanel.add(durdurButton);

        JButton fotoCekButton = new JButton("Fotoğraf Çek");
        bottomPanel.add(fotoCekButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Sağ alttaki gönder butonu
        JButton gonderButton = new JButton("Gönder");
        frame.add(gonderButton, BorderLayout.EAST);

        frame.setVisible(true);
    }
}
}
