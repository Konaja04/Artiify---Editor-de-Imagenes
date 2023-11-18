/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete.clases;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author KONAHA
 */
public class BIENVENIDOXD extends javax.swing.JFrame 
{
    private BufferedImage IMAGEN,IMGINSERTAR;
    private String route;
    private File nombre;
    
    public BIENVENIDOXD() throws IOException {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
        this.setLocationRelativeTo(null);
        IMGINSERTAR = ImageIO.read(new File("src\\dragimage.png\\"));
        InsertarImagen(this.DRAG_DROP, IMGINSERTAR);
        //
        OverlayLayout overlayLayout = new OverlayLayout(DRAG_DROP);
        DRAG_DROP.setLayout(overlayLayout);
        modificarLabel();
        //
        JButton boton = new JButton();
        ImageIcon icono = new ImageIcon(getClass().getResource("/boton.png"));
        ImageIcon iconoPresionado = new ImageIcon(getClass().getResource("/botonpres.png"));
        boton.setIcon(icono);
        ButtonModel buttonModel = boton.getModel();
        buttonModel.addChangeListener(e -> {
            if (buttonModel.isPressed()) {
                boton.setIcon(iconoPresionado);
            } else {
                boton.setIcon(icono);
            }
        });
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setIcon(iconoPresionado);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (buttonModel.isPressed()) {
                    boton.setIcon(iconoPresionado);
                } else {
                    boton.setIcon(icono);
                }
            }
        });
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        DRAG_DROP.add(boton);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setAlignmentY(0.8f);
        boton.addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
            JFileChooser archivo = new JFileChooser();
            int result = archivo.showOpenDialog(boton);
            if(result != JFileChooser.CANCEL_OPTION)
            {
                nombre= archivo.getSelectedFile();
                route= nombre.getAbsolutePath();
                System.out.println(route);
                try {
                    IMAGEN = ImageIO.read(new File(route));
                } 
                catch (IOException ex) {
                    Logger.getLogger(BIENVENIDOXD.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }
            if(IMAGEN!=null)
            {
                dispose();
                Ventana ventana = new Ventana(IMAGEN);
                ventana.setVisible(true);
            }
            }
        });
        
    }
    class JPanelGradient extends JPanel
    {
        protected void paintComponent(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g;
            int Width = getWidth();
            int Height = getHeight();
            Color gris = new Color(102,102,102);
            Color grisClaro = new Color(150,150,150);
            
            GradientPaint gp = new GradientPaint(0,0,gris,180,Height,grisClaro);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, Width, Height);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel2 = new JPanelGradient();
        DRAG_DROP = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        AbrirBotton = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        SalirButton = new javax.swing.JMenuItem();

        jScrollPane1.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bienvenido a Artify");
        setResizable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(DRAG_DROP, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(DRAG_DROP, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        AbrirBotton.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Abrir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        AbrirBotton.add(jMenuItem1);

        SalirButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        SalirButton.setText("Salir");
        SalirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirButtonActionPerformed(evt);
            }
        });
        AbrirBotton.add(SalirButton);

        jMenuBar1.add(AbrirBotton);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JFileChooser archivo = new JFileChooser();
        int result = archivo.showOpenDialog(this);
        if(result != JFileChooser.CANCEL_OPTION)
        {
            nombre= archivo.getSelectedFile();
            route= nombre.getAbsolutePath();
            System.out.println(route);
            try {
                IMAGEN = ImageIO.read(new File(route));
            } 
            catch (IOException ex) {
                Logger.getLogger(BIENVENIDOXD.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        if(IMAGEN!=null)
        {
            dispose();
            Ventana ventana = new Ventana(IMAGEN);
            ventana.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed
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
                    String cad=t.getTransferData(DataFlavor.javaFileListFlavor).toString();
                    String resultado = cad.replace("[", "").replace("]", "");
                    System.out.println(resultado);
                    IMAGEN = ImageIO.read(new File(resultado));
                    if(IMAGEN!=null)
                    {
                    dispose();
                    Ventana ventana = new Ventana(IMAGEN);
                    ventana.setVisible(true);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "¡Introduce un formato de imagen válido!", "Alerta", JOptionPane.WARNING_MESSAGE);
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
        this.DRAG_DROP.setTransferHandler(th); 
    }
    private void SalirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_SalirButtonActionPerformed
    private void InsertarImagen(JLabel LabelImagen,BufferedImage IMAGEN) {
        int originalWidth = IMAGEN.getWidth();
        int originalHeight = IMAGEN.getHeight();
        int lblWidth = LabelImagen.getWidth();
        int lblHeight = LabelImagen.getHeight();
        int newWidth, newHeight;
        if (originalWidth > originalHeight) {
            newWidth = lblWidth;
            newHeight = (originalHeight * lblWidth) / originalWidth;
        } else {
            newHeight = lblHeight;
            newWidth = (originalWidth * lblHeight) / originalHeight;
        }
        BufferedImage imagenRedimensionada = new BufferedImage(newWidth, newHeight, IMAGEN.getType());
        imagenRedimensionada.getGraphics().drawImage(IMAGEN.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        LabelImagen.setIcon(new ImageIcon(imagenRedimensionada));
        LabelImagen.repaint();
    }    
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu AbrirBotton;
    private javax.swing.JLabel DRAG_DROP;
    private javax.swing.JMenuItem SalirButton;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables


}
