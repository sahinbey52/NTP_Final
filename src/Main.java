import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.awt.image.ColorConvertOp;
import java.awt.image.RescaleOp;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

public class Main extends Fotograf {
    //Bazı static tanımlamalar
    private static boolean grilestir = false; // Grileştirme durumunu tutmak için bir bayrak değişkeni
    private static boolean aydinlat = false; // Aydınlatma durumunu tutmak için bir bayrak değişkeni
    private static boolean parlat = false;   // Parlaklık durumunu tutmak için bir bayrak değişkeni
    public static BufferedImage capturedImage = null;
    static BufferedImage image;
    static final Lock lock=new ReentrantLock();

    static final Condition condition = lock.newCondition();
    static boolean captureBlock=false;


    public static void main(String[] args) {

        // Kamera yakalama nesnesi oluştur
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {

            // Kamera yakalama işlemini başlat
            grabber.start();

            // Kameradan alınan görüntüyü Java 2D görüntüsüne dönüştür
            Java2DFrameConverter converter = new Java2DFrameConverter();
            image = converter.getBufferedImage(grabber.grab(), 1);

            // Yeni bir JFrame oluştur ve başlık olarak "Kamera Görüntüsü" kullan
            JFrame frame = new JFrame("Kamera Görüntüsü");

            // Arka plan rengi
            Color backgroundColor = new Color(234, 234, 234); // Yumuşak Gri
            frame.getContentPane().setBackground(backgroundColor);

            //Renkleri tanımla
            Color Renk1 = Color.decode("#3A2D27");
            Color Renk2 = Color.decode("#A99165");
            Color Renk3 = Color.decode("#C9C69F");
            Color Renk4 = Color.decode("#594A3C");

            // image adlı görüntüyü (resmi) belirtilen koordinatlarda çizer
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 8, 3, null);
                }

            };
            panel.setBackground(Renk4);

            //Filtre butonu init
            JButton grilestirButton = new JButton("Grileştir");
            JButton parlatButton = new JButton("Parlat");
            JButton aydinlatButton = new JButton("Aydınlat");

            // Arka plan rengini değiştir
            grilestirButton.setBackground(Renk2);
            parlatButton.setBackground(Renk2);
            aydinlatButton.setBackground(Renk2);

            //Grilestir butonuna tıklanırsa
            grilestirButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    grilestir = !grilestir;
                    aydinlat = false;
                    parlat = false;

                }
            });
            //Aydinlat butonuna tıklanırsa
            aydinlatButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    aydinlat = !aydinlat;
                    grilestir = false;
                    parlat = false;
                }
            });
            //Parlat butonuna tıklanırsa
            parlatButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    parlat = !parlat;
                    grilestir = false;
                    aydinlat = false;
                }
            });

            // Pencere kapatıldığında uygulamanın tamamen sonlandırılmasını sağla
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1385, 560);
            frame.setResizable(false); // Ekran boyutunu değiştirmeyi engelle
            frame.setLocationRelativeTo(null); // Ekranın ortasında konumlandır
            frame.setVisible(true);
            frame.setLayout(new BorderLayout());

            // Altta bulunan butonlar bölümü
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            bottomPanel.setBackground(Renk1);

            //Alt tarafa konulacak butonlar init edilir.
            JButton fotoCekButton = new JButton("Fotoğraf Çek");
            bottomPanel.add(fotoCekButton);
            JButton fotoKaydetButton = new JButton("Fotoğraf Kaydet");
            bottomPanel.add(fotoKaydetButton);
            JButton paylasButton = new JButton("Fotografi Facebook'ta Paylaş");
            bottomPanel.add(paylasButton);

            //Butonların arka planı renklendirilir.
            fotoCekButton.setBackground(Renk3);
            paylasButton.setBackground(Renk3);
            fotoKaydetButton.setBackground(Renk3);

            frame.add(bottomPanel, BorderLayout.SOUTH);

            // Soldaki butonlar bölümü
            JPanel buttonsPanel = new JPanel(new GridLayout(3, 1));
            buttonsPanel.setBackground(Renk1);

            buttonsPanel.add(grilestirButton);
            buttonsPanel.add(aydinlatButton);
            buttonsPanel.add(parlatButton);

            frame.getContentPane().add(panel);
            frame.add(buttonsPanel,BorderLayout.WEST);

            // Yeni bir JPanel oluştur ve içindeki bileşenlerin yerleşimini GridBagLayout kullanarak yönet
            JPanel rightPanel = new JPanel(new GridBagLayout());

            // Yerleşim kısıtlamaları için GridBagConstraints nesnesi oluştur
            // anchor özelliği, bileşenin hücre içindeki konumunu belirler.
            // GridBagConstraints.NORTHEAST, bileşenin hücrenin kuzeydoğu köşesine hizalanacağını belirtir.
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.NORTHEAST;

            // JLabel nesnesi oluştur
            JLabel imageLabel = new JLabel();

            // Bileşenin yerleştirileceği hücrenin koordinatlarını ve boyutlarını belirle
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // 2 hücre genişliği
            gbc.gridheight = 1; // 1 hücre yüksekliği
            gbc.insets = new Insets(0, 0, 0, 0); // Üst boşluğu kaldırma
            rightPanel.add(imageLabel, gbc);

            frame.add(rightPanel, BorderLayout.EAST);
            frame.revalidate();

            fotoCekButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        // Kilitleme mekanizması başlat
                        lock.lock();

                        // captureBlock true olduğu sürece beklemeye al
                        while (captureBlock){
                            condition.await();
                        }
                        // capturedImage, image'ın bir kopyası olarak kaydedilir
                        capturedImage =fotoKopyala(image);
                        // Kilitleme mekanizmasını serbest bırak
                        lock.unlock();

                        // imageLabel'in ikonunu yakalanan görüntüye ayarla
                        imageLabel.setIcon(new ImageIcon(capturedImage));

                        // foto, capturedImage'ın bir kopyası olarak kaydedilir
                        foto = fotoKopyala(capturedImage);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            fotoKaydetButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //Fotograf katme fonksiyonu calisir
                    fotografKaydet();
                }
            });

            paylasButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //Fotograf paylasma foksiiyonu calisir
                    fotoPaylas();
                }
            });



            while (frame.isVisible()) {
                // Kilitleme mekanizmasını başlat
                lock.lock();
                captureBlock=true;
                // Kameradan bir görüntü al ve image değişkenine at
                image = converter.getBufferedImage(grabber.grab(), 1);

                if (grilestir) {
                    // Yeni bir BufferedImage oluştur ve gri tonlama için TYPE_BYTE_GRAY kullan
                    BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                    ColorConvertOp op = new ColorConvertOp(null);
                    // image'ı grayImage'a dönüştür
                    op.filter(image, grayImage);
                    image = grayImage;
                    //capturedImage = grayImage; SİLİNECEK!!!


                } else if (aydinlat) {
                    float brightness = 1.2f;    // Aydınlatma faktörü
                    float offset = 0;           // Aydınlatma ofseti
                    RescaleOp rescaleOp = new RescaleOp(brightness, offset, null);
                    rescaleOp.filter(image, image);
                    //capturedImage = rescaleOp.filter(image, image); SİLİNECEK'''
                } else if (parlat) {

                    float brightness = 1.2f;    // Parlaklık faktörü
                    float offset = 20;          // Parlaklık ofseti
                    RescaleOp rescaleOp = new RescaleOp(brightness, offset, null);
                    rescaleOp.filter(image, image);

                }
                captureBlock=false;        // captureBlock değerini false olarak ayarla
                condition.signalAll();     // condition nesnesine sinyal gönder, bekleyen tüm thread'leri uyandır
                lock.unlock();             // Kilitleme mekanizmasını serbest bırak

                panel.repaint();           // Panelin yeniden çizilmesini sağlar
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                grabber.stop();
                grabber.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
