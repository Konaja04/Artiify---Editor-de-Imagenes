/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete.clases;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.scene.effect.GaussianBlur;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JViewport;
import javax.swing.OverlayLayout;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author KONAHA
 */
public class Ventana extends javax.swing.JFrame implements WindowListener
{
    private Imagen IMAGENCLASE;
    private BufferedImage[] listaZ= new BufferedImage[1000];
    private BufferedImage[] listaY= new BufferedImage[1000];
    private String[] listaxd = new String[5];
    private String[] listaxdY = new String[5];
    private int contaZ,contaY,contaxd;
    private BufferedImage IMAGEN,IMGaux;
    private String route;
    private int brillo,contraste,w = 0, h = 0;
    private Graphics2D g2d;
    private Point PointOnView = null;
    private boolean archivoGuardado;
    
    public Ventana(BufferedImage imagenConstructor) {
        super();
        initComponents();
        OverlayLayout overlayLayout = new OverlayLayout(LabelImagen);
        this.LabelImagen.setLayout(overlayLayout);
        modificarLabel();
        setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
        this.setLocationRelativeTo(null);
        this.setExtendedState(this.MAXIMIZED_BOTH);  
        contaZ=0;
        contaY=0;
        IMAGEN=imagenConstructor;
        IMAGENCLASE= new Imagen(IMAGEN);
        LabelImagen.setHorizontalAlignment(JLabel.CENTER);
        LabelImagen.setVerticalAlignment(JLabel.CENTER);
        w = jScrollPane1.getHeight();
        h = jScrollPane1.getWidth();
        InsertarImagen(IMAGEN);
        IMGaux=IMAGEN;
        listaZ[contaZ]=IMAGEN;
        contaZ++;
        archivoGuardado = true;
        
        // Agregar un WindowListener para capturar el evento de cierre de ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!archivoGuardado) {
                    int opcion = JOptionPane.showConfirmDialog(
                            Ventana.this,
                            "¿Deseas guardar el archivo antes de salir?",
                            "Guardar archivo",
                            JOptionPane.YES_NO_CANCEL_OPTION
                    );

                    if (opcion == JOptionPane.YES_OPTION) {
                        if (IMAGENCLASE != null && IMAGENCLASE.getImg() != null) {
                        JFileChooser archivo = new JFileChooser();
                        int result = archivo.showSaveDialog(archivo);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                File archivoGuardar = archivo.getSelectedFile();
                                String rutaGuardar = archivoGuardar.getAbsolutePath();
                                File archivoSalida = new File(rutaGuardar);
                                try {
                                    ImageIO.write(IMGaux, "JPEG", archivoSalida);
                                } catch (IOException ex) {
                                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        archivoGuardado = true;
                    } else if (opcion == JOptionPane.NO_OPTION) {
                        Ventana.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    } else {
                        Ventana.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                } else {
                    Ventana.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
        
    }

    public void modificarLabel()
    {
        TransferHandler th = new TransferHandler()
        {
            @Override
            public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
                return true; 
            }
            @Override
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        if (!archivoGuardado) {
                            int opcion = JOptionPane.showConfirmDialog(
                                    Ventana.this,
                                    "¿Deseas guardar el archivo antes de salir?",
                                    "Guardar archivo",
                                    JOptionPane.YES_NO_CANCEL_OPTION
                            );

                            if (opcion == JOptionPane.YES_OPTION) {
                                if (IMAGENCLASE != null && IMAGENCLASE.getImg() != null) {
                                JFileChooser archivo = new JFileChooser();
                                int result = archivo.showSaveDialog(archivo);
                                    if (result == JFileChooser.APPROVE_OPTION) {
                                        File archivoGuardar = archivo.getSelectedFile();
                                        String rutaGuardar = archivoGuardar.getAbsolutePath();
                                        File archivoSalida = new File(rutaGuardar);
                                        try {
                                            ImageIO.write(IMGaux, "JPEG", archivoSalida);
                                        } catch (IOException ex) {
                                            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                                archivoGuardado = true;
                            //
                            String cad=t.getTransferData(DataFlavor.javaFileListFlavor).toString();
                            String resultado = cad.replace("[", "").replace("]", "");
                            System.out.println(resultado);
                            IMAGEN = ImageIO.read(new File(resultado));
                            if(IMAGEN!=null)
                            {
                                contaZ=0;
                                contaY=0;
                                IMAGENCLASE= new Imagen(IMAGEN);
                                InsertarImagen(IMAGEN);
                                IMGaux=IMAGEN;
                                listaZ[contaZ]=IMAGEN;
                                contaZ++; 
                                archivoGuardado = true;
                                
                                
                                
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "¡Introduce un formato de imagen válido!", "Alerta", JOptionPane.WARNING_MESSAGE);
                            } 
                            //
                                
                            } else if (opcion == JOptionPane.NO_OPTION) {
                                String cad=t.getTransferData(DataFlavor.javaFileListFlavor).toString();
                                String resultado = cad.replace("[", "").replace("]", "");
                                System.out.println(resultado);
                                IMAGEN = ImageIO.read(new File(resultado));
                                if(IMAGEN!=null)
                                { 
                                    contaZ=0;
                                    contaY=0;
                                    IMAGENCLASE= new Imagen(IMAGEN);
                                    InsertarImagen(IMAGEN);
                                    IMGaux=IMAGEN;
                                    listaZ[contaZ]=IMAGEN;
                                    contaZ++; 
                                    archivoGuardado = true;
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "¡Introduce un formato de imagen válido!", "Alerta", JOptionPane.WARNING_MESSAGE);
                                } 
                                //
                            } else {
                                Ventana.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            }
                        } 
                        else {
                            //
                            String cad=t.getTransferData(DataFlavor.javaFileListFlavor).toString();
                            String resultado = cad.replace("[", "").replace("]", "");
                            System.out.println(resultado);
                            IMAGEN = ImageIO.read(new File(resultado));
                            if(IMAGEN!=null)
                            { 
                                contaZ=0;
                                contaY=0;
                                IMAGENCLASE= new Imagen(IMAGEN);
                                InsertarImagen(IMAGEN);
                                IMGaux=IMAGEN;
                                listaZ[contaZ]=IMAGEN;
                                contaZ++; 
                                archivoGuardado = true;
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "¡Introduce un formato de imagen válido!", "Alerta", JOptionPane.WARNING_MESSAGE);
                            } 
                            //
                        }
                    }
                } catch (UnsupportedFlavorException ex) {
                    Logger.getLogger(BIENVENIDOXD.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BIENVENIDOXD.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }
        };  
        this.LabelImagen.setTransferHandler(th); 
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        LabelImagen = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        BrilloSlider = new javax.swing.JSlider();
        ContrasteSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        RedSlider = new javax.swing.JSlider();
        GreenSlider = new javax.swing.JSlider();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        BlueSlider = new javax.swing.JSlider();
        jMenuBar1 = new javax.swing.JMenuBar();
        AbrirBotton = new javax.swing.JMenu();
        AbrirButotn = new javax.swing.JMenuItem();
        GuardarButton = new javax.swing.JMenuItem();
        Jmenuxd = new javax.swing.JMenu();
        DeshacerButton = new javax.swing.JMenuItem();
        RehacerButton = new javax.swing.JMenuItem();
        ZoomInButton = new javax.swing.JMenuItem();
        ZoomOutButton = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        DesaturarButton = new javax.swing.JMenuItem();
        InvertirButton = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        BoxBlurBottom = new javax.swing.JMenuItem();
        GaussianBliurButton = new javax.swing.JMenuItem();
        SobelButton = new javax.swing.JMenuItem();
        HighPassButton = new javax.swing.JMenuItem();
        PixelarButton = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        MediaButton = new javax.swing.JMenuItem();
        MedianaButton = new javax.swing.JMenuItem();
        LaplaceButton = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Artify");

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jScrollPane1.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setToolTipText("");
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setDebugGraphicsOptions(javax.swing.DebugGraphics.BUFFERED_OPTION);
        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setOpaque(false);

        LabelImagen.setBackground(new java.awt.Color(51, 51, 51));
        LabelImagen.setForeground(new java.awt.Color(0, 21, 15));
        LabelImagen.setToolTipText("");
        LabelImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LabelImagen.setOpaque(true);
        LabelImagen.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                LabelImagenMouseDragged(evt);
            }
        });
        LabelImagen.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                LabelImagenMouseWheelMoved(evt);
            }
        });
        LabelImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                LabelImagenMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LabelImagenMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(LabelImagen);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));

        BrilloSlider.setBackground(new java.awt.Color(102, 102, 102));
        BrilloSlider.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        BrilloSlider.setForeground(new java.awt.Color(255, 255, 255));
        BrilloSlider.setMajorTickSpacing(5);
        BrilloSlider.setMaximum(25);
        BrilloSlider.setMinimum(-25);
        BrilloSlider.setPaintLabels(true);
        BrilloSlider.setPaintTicks(true);
        BrilloSlider.setValue(0);
        BrilloSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                BrilloSliderStateChanged(evt);
            }
        });
        BrilloSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                BrilloSliderMouseReleased(evt);
            }
        });

        ContrasteSlider.setBackground(new java.awt.Color(102, 102, 102));
        ContrasteSlider.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ContrasteSlider.setForeground(new java.awt.Color(255, 255, 255));
        ContrasteSlider.setMajorTickSpacing(5);
        ContrasteSlider.setMaximum(25);
        ContrasteSlider.setMinimum(-25);
        ContrasteSlider.setPaintLabels(true);
        ContrasteSlider.setPaintTicks(true);
        ContrasteSlider.setValue(0);
        ContrasteSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ContrasteSliderStateChanged(evt);
            }
        });
        ContrasteSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ContrasteSliderMouseReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Contraste:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Brillo:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BrilloSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ContrasteSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BrilloSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ContrasteSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel2.setBackground(new java.awt.Color(102, 102, 102));
        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("   Brillo y Contraste");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("   Equilibrio de Color");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Canal Rojo:");

        RedSlider.setBackground(new java.awt.Color(102, 102, 102));
        RedSlider.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        RedSlider.setForeground(new java.awt.Color(255, 255, 255));
        RedSlider.setMajorTickSpacing(10);
        RedSlider.setMaximum(50);
        RedSlider.setMinimum(-50);
        RedSlider.setPaintLabels(true);
        RedSlider.setPaintTicks(true);
        RedSlider.setValue(0);
        RedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RedSliderStateChanged(evt);
            }
        });
        RedSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                RedSliderMouseReleased(evt);
            }
        });

        GreenSlider.setBackground(new java.awt.Color(102, 102, 102));
        GreenSlider.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        GreenSlider.setForeground(new java.awt.Color(255, 255, 255));
        GreenSlider.setMajorTickSpacing(10);
        GreenSlider.setMaximum(50);
        GreenSlider.setMinimum(-50);
        GreenSlider.setPaintLabels(true);
        GreenSlider.setPaintTicks(true);
        GreenSlider.setValue(0);
        GreenSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                GreenSliderStateChanged(evt);
            }
        });
        GreenSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                GreenSliderMouseReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Canal Verde:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Canal Azul:");

        BlueSlider.setBackground(new java.awt.Color(102, 102, 102));
        BlueSlider.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        BlueSlider.setForeground(new java.awt.Color(255, 255, 255));
        BlueSlider.setMajorTickSpacing(10);
        BlueSlider.setMaximum(50);
        BlueSlider.setMinimum(-50);
        BlueSlider.setPaintLabels(true);
        BlueSlider.setPaintTicks(true);
        BlueSlider.setValue(0);
        BlueSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                BlueSliderStateChanged(evt);
            }
        });
        BlueSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                BlueSliderMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BlueSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GreenSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GreenSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BlueSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        AbrirBotton.setText("Archivo");

        AbrirButotn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        AbrirButotn.setText("Abrir");
        AbrirButotn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirButotnActionPerformed(evt);
            }
        });
        AbrirBotton.add(AbrirButotn);

        GuardarButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        GuardarButton.setText("Guardar");
        GuardarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarButtonActionPerformed(evt);
            }
        });
        AbrirBotton.add(GuardarButton);

        jMenuBar1.add(AbrirBotton);

        Jmenuxd.setText("Edición");

        DeshacerButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        DeshacerButton.setText("Deshacer");
        DeshacerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeshacerButtonActionPerformed(evt);
            }
        });
        Jmenuxd.add(DeshacerButton);

        RehacerButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        RehacerButton.setText("Rehacer");
        RehacerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RehacerButtonActionPerformed(evt);
            }
        });
        Jmenuxd.add(RehacerButton);

        ZoomInButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ADD, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        ZoomInButton.setText("Zoom In (+)");
        ZoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ZoomInButtonActionPerformed(evt);
            }
        });
        Jmenuxd.add(ZoomInButton);

        ZoomOutButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SUBTRACT, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        ZoomOutButton.setText("Zoom Out (-)");
        ZoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ZoomOutButtonActionPerformed(evt);
            }
        });
        Jmenuxd.add(ZoomOutButton);

        jMenuBar1.add(Jmenuxd);

        jMenu2.setText("Imagen");

        DesaturarButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        DesaturarButton.setText("Desaturar");
        DesaturarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DesaturarButtonActionPerformed(evt);
            }
        });
        jMenu2.add(DesaturarButton);

        InvertirButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        InvertirButton.setText("Invertir");
        InvertirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InvertirButtonActionPerformed(evt);
            }
        });
        jMenu2.add(InvertirButton);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Filtros");

        BoxBlurBottom.setText("Box Blur");
        BoxBlurBottom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoxBlurBottomActionPerformed(evt);
            }
        });
        jMenu3.add(BoxBlurBottom);

        GaussianBliurButton.setText("Gaussian Blur");
        GaussianBliurButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GaussianBliurButtonActionPerformed(evt);
            }
        });
        jMenu3.add(GaussianBliurButton);

        SobelButton.setText("Sobel ");
        SobelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SobelButtonActionPerformed(evt);
            }
        });
        jMenu3.add(SobelButton);

        HighPassButton.setText("Paso Alto");
        HighPassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HighPassButtonActionPerformed(evt);
            }
        });
        jMenu3.add(HighPassButton);

        PixelarButton.setText("Pixelar");
        PixelarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PixelarButtonActionPerformed(evt);
            }
        });
        jMenu3.add(PixelarButton);

        jMenu1.setText("Reducción de Ruido");

        MediaButton.setText("Media");
        MediaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MediaButtonActionPerformed(evt);
            }
        });
        jMenu1.add(MediaButton);

        MedianaButton.setText("Mediana");
        MedianaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MedianaButtonActionPerformed(evt);
            }
        });
        jMenu1.add(MedianaButton);

        jMenu3.add(jMenu1);

        LaplaceButton.setText("Laplace");
        LaplaceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LaplaceButtonActionPerformed(evt);
            }
        });
        jMenu3.add(LaplaceButton);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    private void AbrirButotnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirButotnActionPerformed
        // TODO add your handling code here:
        JFileChooser archivo = new JFileChooser();
        w = jScrollPane1.getHeight();
        h = jScrollPane1.getWidth();
        int result = archivo.showOpenDialog(this);
        contaZ=0;
        contaY=0;
        if(result != JFileChooser.CANCEL_OPTION)
        {
            File nombre= archivo.getSelectedFile();
            route= nombre.getAbsolutePath();
            try {
                IMAGEN = ImageIO.read(new File(route));
            } 
            catch (IOException ex) {
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
            }
            IMAGENCLASE= new Imagen(IMAGEN);
            InsertarImagen(IMAGEN);
            IMGaux=IMAGEN;
            listaZ[contaZ]=IMAGEN;
            contaZ++; 
        }
        
        
    }//GEN-LAST:event_AbrirButotnActionPerformed

    private void DesaturarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DesaturarButtonActionPerformed
        // TODO add your handling code here:
        if(IMGaux!=null)
        {
        IMGaux=IMAGENCLASE.EscaladeGrises(IMGaux);
        InsertarImagen(IMGaux);
        IMAGEN=IMGaux;
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
        archivoGuardado = false;
        }
    }//GEN-LAST:event_DesaturarButtonActionPerformed

    private void InvertirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InvertirButtonActionPerformed
        // TODO add your handling code here:
        IMGaux=IMAGENCLASE.Invertir(IMGaux);
        InsertarImagen(IMGaux);
        IMAGEN=IMGaux;
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
        archivoGuardado = false;
    }//GEN-LAST:event_InvertirButtonActionPerformed
    
    private void BrilloSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_BrilloSliderStateChanged
        int Brillo = this.BrilloSlider.getValue();
        int Red = this.RedSlider.getValue();
        int Green = this.GreenSlider.getValue();
        int Blue = this.BlueSlider.getValue();
        int Contraste = this.ContrasteSlider.getValue();
        BufferedImage BrilloIMG= new BufferedImage(IMAGEN.getWidth(),IMAGEN.getHeight(),BufferedImage.TYPE_INT_RGB);
        BrilloIMG=AjusteColor(IMAGEN, Brillo,Red,Green,Blue,Contraste);
        InsertarImagen(BrilloIMG); 
        IMGaux=BrilloIMG;
    }//GEN-LAST:event_BrilloSliderStateChanged

    private void BoxBlurBottomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoxBlurBottomActionPerformed
        // TODO add your handling code here:
        BoxBlurVentana ventana= new BoxBlurVentana(IMGaux);
        ventana.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
        // La ventana interna se ha cerrado
        if(ventana.GetImagen()!=null)
        {
        archivoGuardado = false;
        IMGaux=ventana.GetImagen();
        IMAGEN=IMGaux;
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
        BrilloSlider.setValue(0);
        InsertarImagen(IMGaux);
        }
        }
        });
        ventana.setVisible(true);
    }//GEN-LAST:event_BoxBlurBottomActionPerformed

    private void GaussianBliurButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GaussianBliurButtonActionPerformed
        // TODO add your handling code here
        GaussianVentana ventanaGaussian= new GaussianVentana(IMGaux);
        ventanaGaussian.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
        // La ventana interna se ha cerrado
        if(ventanaGaussian.GetImagen()!=null)
        {
        archivoGuardado = false;
        IMGaux=ventanaGaussian.GetImagen();
        IMAGEN=IMGaux;
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
        BrilloSlider.setValue(0);
        InsertarImagen(IMGaux);
        }
        }
        });
        ventanaGaussian.setVisible(true);
    }//GEN-LAST:event_GaussianBliurButtonActionPerformed

    private void ContrasteSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ContrasteSliderStateChanged
        // TODO add your handling code here:
        int Brillo = this.BrilloSlider.getValue();
        int Red = this.RedSlider.getValue();
        int Green = this.GreenSlider.getValue();
        int Blue = this.BlueSlider.getValue();
        int Contraste = this.ContrasteSlider.getValue();
        BufferedImage BrilloIMG= new BufferedImage(IMAGEN.getWidth(),IMAGEN.getHeight(),BufferedImage.TYPE_INT_RGB);
        BrilloIMG=AjusteColor(IMAGEN, Brillo,Red,Green,Blue,Contraste);
        InsertarImagen(BrilloIMG); 
        IMGaux=BrilloIMG;
    }//GEN-LAST:event_ContrasteSliderStateChanged

    private void GuardarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarButtonActionPerformed
        // TODO add your handling code here:   
        if (IMAGENCLASE != null && IMAGENCLASE.getImg() != null) {
        JFileChooser archivo = new JFileChooser();
        int result = archivo.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivoGuardar = archivo.getSelectedFile();
            String rutaGuardar = archivoGuardar.getAbsolutePath();
            File archivoSalida = new File(rutaGuardar);
            try {
                ImageIO.write(IMGaux, "JPEG", archivoSalida);
            } catch (IOException ex) {
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
            }
            archivoGuardado = true;
        }
        }
    }//GEN-LAST:event_GuardarButtonActionPerformed

    private void DeshacerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeshacerButtonActionPerformed
        // TODO add your handling code here:
        if(contaZ>1)
        { 
        listaY[contaY]=listaZ[contaZ-1];
        contaZ--;
        IMGaux=listaZ[contaZ-1];
        IMAGEN=IMGaux;
        LabelImagen.setIcon(null);
        InsertarImagen(IMGaux);
        contaY++; 
        archivoGuardado = false;
        }
    }//GEN-LAST:event_DeshacerButtonActionPerformed

    private void HighPassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HighPassButtonActionPerformed
        // TODO add your handling code here:
        HighPassVentana ventana= new HighPassVentana(IMGaux);
        ventana.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
        // La ventana interna se ha cerrado
        if(ventana.GetImagen()!=null)
        {
        archivoGuardado = false;
        IMGaux=ventana.GetImagen();
        IMAGEN=IMGaux;
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
        BrilloSlider.setValue(0);
        InsertarImagen(IMGaux);
        }
        }
        });
        ventana.setVisible(true);
    }//GEN-LAST:event_HighPassButtonActionPerformed

    private void RehacerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RehacerButtonActionPerformed
        // TODO add your handling code her
        if(contaY>0)
        {
        archivoGuardado = false;    
        listaZ[contaZ]=listaY[contaY-1];
        IMGaux=listaY[contaY-1];
        IMAGEN=IMGaux;
        LabelImagen.setIcon(null);
        InsertarImagen(IMGaux);
        contaZ++;
        contaY--;
        }
    }//GEN-LAST:event_RehacerButtonActionPerformed

    private void SobelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SobelButtonActionPerformed
        // TODO add your handling code here:
        SobelVentana ventana= new SobelVentana(IMGaux);
        ventana.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
        // La ventana interna se ha cerrado
        if(ventana.GetImagen()!=null)
        {
        archivoGuardado = false;
        IMGaux=ventana.GetImagen();
        IMAGEN=IMGaux;
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
        BrilloSlider.setValue(0);
        InsertarImagen(IMGaux);
        }
        }
        });
        ventana.setVisible(true);
    }//GEN-LAST:event_SobelButtonActionPerformed

    private void PixelarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PixelarButtonActionPerformed
        // TODO add your handling code here:
        PixelarVentana ventana= new PixelarVentana(IMGaux);
        ventana.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
        // La ventana interna se ha cerrado
        if(ventana.GetImagen()!=null)
        {
        archivoGuardado = false;
        IMGaux=ventana.GetImagen();
        IMAGEN=IMGaux;
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
        BrilloSlider.setValue(0);
        InsertarImagen(IMGaux);
        }
        }
        });
        ventana.setVisible(true);
    }//GEN-LAST:event_PixelarButtonActionPerformed

    private void MediaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MediaButtonActionPerformed
        // TODO add your handling code here:
        MediaVentana ventana= new MediaVentana(IMGaux);
        ventana.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
        // La ventana interna se ha cerrado
        if(ventana.GetImagen()!=null)
        {
        archivoGuardado = false;
        IMGaux=ventana.GetImagen();
        IMAGEN=IMGaux;
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
        BrilloSlider.setValue(0);
        InsertarImagen(IMGaux);
        }
        }
        });
        ventana.setVisible(true);
    }//GEN-LAST:event_MediaButtonActionPerformed

    private void MedianaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MedianaButtonActionPerformed
        // TODO add your handling code here:
        MedianaVentana ventana= new MedianaVentana(IMGaux);
        ventana.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
        // La ventana interna se ha cerrado
        if(ventana.GetImagen()!=null)
        {
        archivoGuardado = false;
        IMGaux=ventana.GetImagen();
        IMAGEN=IMGaux;
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
        BrilloSlider.setValue(0);
        InsertarImagen(IMGaux);
        }
        }
        });
        ventana.setVisible(true);
    }//GEN-LAST:event_MedianaButtonActionPerformed

    private void LaplaceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LaplaceButtonActionPerformed
        // TODO add your handling code here:
        LaplaceVentana ventana= new LaplaceVentana(IMGaux);
        ventana.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
        // La ventana interna se ha cerrado
        if(ventana.GetImagen()!=null)
        {
        archivoGuardado = false;
        IMGaux=ventana.GetImagen();
        IMAGEN=IMGaux;
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
        BrilloSlider.setValue(0);
        InsertarImagen(IMGaux);
        }
        }
        });
        ventana.setVisible(true);
    }//GEN-LAST:event_LaplaceButtonActionPerformed

    private void RedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RedSliderStateChanged
        // TODO add your handling code here:
        int Brillo = this.BrilloSlider.getValue();
        int Red = this.RedSlider.getValue();
        int Green = this.GreenSlider.getValue();
        int Blue = this.BlueSlider.getValue();
        int Contraste = this.ContrasteSlider.getValue();
        BufferedImage BrilloIMG= new BufferedImage(IMAGEN.getWidth(),IMAGEN.getHeight(),BufferedImage.TYPE_INT_RGB);
        BrilloIMG=AjusteColor(IMAGEN, Brillo,Red,Green,Blue,Contraste);
        InsertarImagen(BrilloIMG); 
        IMGaux=BrilloIMG;
    }//GEN-LAST:event_RedSliderStateChanged

    private void GreenSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_GreenSliderStateChanged
        // TODO add your handling code here:
        int Brillo = this.BrilloSlider.getValue();
        int Red = this.RedSlider.getValue();
        int Green = this.GreenSlider.getValue();
        int Blue = this.BlueSlider.getValue();
        int Contraste = this.ContrasteSlider.getValue();
        BufferedImage BrilloIMG= new BufferedImage(IMAGEN.getWidth(),IMAGEN.getHeight(),BufferedImage.TYPE_INT_RGB);
        BrilloIMG=AjusteColor(IMAGEN, Brillo,Red,Green,Blue,Contraste);
        InsertarImagen(BrilloIMG); 
        IMGaux=BrilloIMG;
    }//GEN-LAST:event_GreenSliderStateChanged

    private void BlueSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_BlueSliderStateChanged
        // TODO add your handling code here:
        int Brillo = this.BrilloSlider.getValue();
        int Red = this.RedSlider.getValue();
        int Green = this.GreenSlider.getValue();
        int Blue = this.BlueSlider.getValue();
        int Contraste = this.ContrasteSlider.getValue();
        BufferedImage BrilloIMG= new BufferedImage(IMAGEN.getWidth(),IMAGEN.getHeight(),BufferedImage.TYPE_INT_RGB);
        BrilloIMG=AjusteColor(IMAGEN, Brillo,Red,Green,Blue,Contraste);
        InsertarImagen(BrilloIMG); 
        IMGaux=BrilloIMG;
    }//GEN-LAST:event_BlueSliderStateChanged

    private void BrilloSliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BrilloSliderMouseReleased
        // TODO add your handling code here:
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
    }//GEN-LAST:event_BrilloSliderMouseReleased

    private void RedSliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RedSliderMouseReleased
        // TODO add your handling code here:
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
    }//GEN-LAST:event_RedSliderMouseReleased

    private void GreenSliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GreenSliderMouseReleased
        // TODO add your handling code here:
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
    }//GEN-LAST:event_GreenSliderMouseReleased

    private void BlueSliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BlueSliderMouseReleased
        // TODO add your handling code here:
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
    }//GEN-LAST:event_BlueSliderMouseReleased

    private void ContrasteSliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContrasteSliderMouseReleased
        // TODO add your handling code here:
        listaZ[contaZ]=IMGaux;
        contaZ++;
        contaY=0;
    }//GEN-LAST:event_ContrasteSliderMouseReleased

    private void ZoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ZoomOutButtonActionPerformed
        // TODO add your handling code here:
        h = h - 20;
        w = w - 20;
        InsertarImagen(IMGaux);
    }//GEN-LAST:event_ZoomOutButtonActionPerformed

    private void ZoomInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ZoomInButtonActionPerformed
        // TODO add your handling code here:
        h = h + 20;
        w = w + 20;
        InsertarImagen(IMGaux);
    }//GEN-LAST:event_ZoomInButtonActionPerformed

    private void LabelImagenMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelImagenMouseDragged
        // TODO add your handling code here:
        Point dragEventPoint = evt.getPoint();
        JViewport viewport = (JViewport) LabelImagen.getParent();
        Point viewPos = viewport.getViewPosition();
        int maxViewPosX = LabelImagen.getWidth() - viewport.getWidth();
        int maxViewPosY = LabelImagen.getHeight() - viewport.getHeight();
        if (LabelImagen.getWidth() > viewport.getWidth()) {
            viewPos.x -= dragEventPoint.x - PointOnView.x;
            if (viewPos.x < 0) {
                viewPos.x = 0;
                PointOnView.x = dragEventPoint.x;
            }
            if (viewPos.x > maxViewPosX) {
                viewPos.x = maxViewPosX;
                PointOnView.x = dragEventPoint.x;
            }
        }
        if (LabelImagen.getHeight() > viewport.getHeight()) {
            viewPos.y -= dragEventPoint.y - PointOnView.y;
            if (viewPos.y < 0) {
                viewPos.y = 0;
                PointOnView.y = dragEventPoint.y;
            }
            if (viewPos.y > maxViewPosY) {
                viewPos.y = maxViewPosY;
                PointOnView.y = dragEventPoint.y;
            }
        }
        viewport.setViewPosition(viewPos);
    }//GEN-LAST:event_LabelImagenMouseDragged

    private void LabelImagenMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_LabelImagenMouseWheelMoved
        // TODO add your handling code here:
        if (evt.getWheelRotation() < 0) {
            h = h + 10;
            w = w + 10;
            InsertarImagen(IMGaux);
            evt.getUnitsToScroll();
        } else {
            h = h - 10;
            w = w - 10;
            InsertarImagen(IMGaux);
            evt.getUnitsToScroll();
        }
    }//GEN-LAST:event_LabelImagenMouseWheelMoved

    private void LabelImagenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelImagenMousePressed
        // TODO add your handling code here:
        LabelImagen.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        PointOnView = evt.getPoint();
    }//GEN-LAST:event_LabelImagenMousePressed

    private void LabelImagenMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelImagenMouseReleased
        // TODO add your handling code here:
        LabelImagen.setCursor(null);
    }//GEN-LAST:event_LabelImagenMouseReleased

    /**
     * @param args the command line arguments
     */
    public BufferedImage AjusteColor(BufferedImage img,int Brillo,int R,int G, int B,int contraste) {
      int width = img.getWidth(); //Ancho de la imagen
      int height = img.getHeight(); //Alto de la imagen
      int brillo = (int)(Brillo*255/100*0.8);
      int RED = (int)(R*255/100);
      int GREEN = (int)(G*255/100);
      int BLUE = (int)(B*255/100);
      int Contraste = contraste*-1;
      BufferedImage aux= new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
      for(int y = 0; y < height; y++){
          for(int x = 0; x < width; x++){//Doble for para recorrer toda la imagen
            int p = img.getRGB(x,y);
            Color color= new Color(p);
            int r = color.getRed();
            int g= color.getGreen();
            int b = color.getBlue();
            int distancia=127-(r+g+b)/3;
            if(distancia<0)distancia*=-1;
            int conts=distancia/50*Contraste;      
            if((r+g+b)/3>127)
            {
                r-=conts;
                g-=conts;
                b-=conts;
            }
            else
            {
                r+=conts;
                g+=conts;
                b+=conts;
            }
            r+=brillo+RED;
            g+=brillo+GREEN;
            b+=brillo+BLUE;
            if(r>255)r=255;
            if(g>255)g=255;
            if(b>255)b=255;
            if(r<0)r=0;
            if(g<0)g=0;
            if(b<0)b=0;
            color= new Color(r,g,b);
            aux.setRGB(x, y,color.getRGB());
            
          }
      }
      archivoGuardado = false;
      return aux;
    }
    private void InsertarImagen(BufferedImage imagen) {
        ImageIcon icono = new ImageIcon(zoom(h, w, imagen));
        this.LabelImagen.setIcon(icono);
        this.LabelImagen.repaint();
    }
    private Image zoom(int targetWidth, int targetHeight, Image img) {
        int originalWidth = img.getWidth(null);
        int originalHeight = img.getHeight(null);

        double scaleX = (double) targetWidth / originalWidth;
        double scaleY = (double) targetHeight / originalHeight;

        double scale = Math.min(scaleX, scaleY);

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        int x = 0;
        int y = 0;
        g2d.drawImage(img, -x, -y, newWidth+x, newHeight+y, null);
        g2d.dispose();

        return resizedImage;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu AbrirBotton;
    private javax.swing.JMenuItem AbrirButotn;
    private javax.swing.JSlider BlueSlider;
    private javax.swing.JMenuItem BoxBlurBottom;
    private javax.swing.JSlider BrilloSlider;
    private javax.swing.JSlider ContrasteSlider;
    private javax.swing.JMenuItem DesaturarButton;
    private javax.swing.JMenuItem DeshacerButton;
    private javax.swing.JMenuItem GaussianBliurButton;
    private javax.swing.JSlider GreenSlider;
    private javax.swing.JMenuItem GuardarButton;
    private javax.swing.JMenuItem HighPassButton;
    private javax.swing.JMenuItem InvertirButton;
    private javax.swing.JMenu Jmenuxd;
    private javax.swing.JLabel LabelImagen;
    private javax.swing.JMenuItem LaplaceButton;
    private javax.swing.JMenuItem MediaButton;
    private javax.swing.JMenuItem MedianaButton;
    private javax.swing.JMenuItem PixelarButton;
    private javax.swing.JSlider RedSlider;
    private javax.swing.JMenuItem RehacerButton;
    private javax.swing.JMenuItem SobelButton;
    private javax.swing.JMenuItem ZoomInButton;
    private javax.swing.JMenuItem ZoomOutButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
