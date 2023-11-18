/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paquete.clases;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author l34314
 */
public class MedianaVentana extends javax.swing.JFrame {

    /**
     * Creates new form GaussianVentana
     */
    private BufferedImage imagenFiltrada,aux;
    private Imagen IMAGENCLASE;
    public MedianaVentana(BufferedImage IMG) {
        initComponents(); 
        setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
        this.PREVBOXLBL.setHorizontalAlignment(JLabel.CENTER);
        this.PREVBOXLBL.setVerticalAlignment(JLabel.CENTER);
        if (IMG== null) {
            this.PREVBOXLBL.setIcon(null);
        } 
        else {
           IMAGENCLASE = new Imagen(IMG);
           this.InsertarImagen(IMAGENCLASE.getImg());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        SLIDER = new javax.swing.JSlider();
        CancelButton = new javax.swing.JButton();
        ApplyButton = new javax.swing.JButton();
        PREVBOXLBL = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reducción de Ruido Mediana");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        SLIDER.setBackground(new java.awt.Color(51, 51, 51));
        SLIDER.setForeground(new java.awt.Color(255, 255, 255));
        SLIDER.setMajorTickSpacing(1);
        SLIDER.setMaximum(10);
        SLIDER.setPaintLabels(true);
        SLIDER.setPaintTicks(true);
        SLIDER.setValue(0);
        SLIDER.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SLIDERStateChanged(evt);
            }
        });
        SLIDER.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SLIDERMouseReleased(evt);
            }
        });

        CancelButton.setText("Cancelar");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        ApplyButton.setText("Aplicar");
        ApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(SLIDER, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(CancelButton)
                .addGap(18, 18, 18)
                .addComponent(ApplyButton)
                .addGap(50, 50, 50))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PREVBOXLBL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PREVBOXLBL, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ApplyButton)
                    .addComponent(CancelButton)
                    .addComponent(SLIDER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SLIDERStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SLIDERStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SLIDERStateChanged

    private void SLIDERMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SLIDERMouseReleased
        // TODO add your handling code here:
        if(IMAGENCLASE!=null)
        {
            int value = this.SLIDER.getValue() * 2;
            this.imagenFiltrada=this.IMAGENCLASE.DenoiseMediana(value);
            InsertarImagen(this.imagenFiltrada);
            aux=imagenFiltrada;
        }
    }//GEN-LAST:event_SLIDERMouseReleased

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        // TODO add your handling code here:
        aux=null;
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed
    public BufferedImage GetImagen()
    {
        return aux;
    }
    private void ApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplyButtonActionPerformed
        // TODO add your handling code here
        this.dispose();
    }//GEN-LAST:event_ApplyButtonActionPerformed


    private void InsertarImagen(BufferedImage imagen) {
        int lblWidth = this.PREVBOXLBL.getWidth();
        int lblHeight = this.PREVBOXLBL.getHeight();

        double widthRatio = (double) lblWidth / imagen.getWidth();
        double heightRatio = (double) lblHeight / imagen.getHeight();
        double scaleFactor = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (imagen.getWidth() * scaleFactor);
        int newHeight = (int) (imagen.getHeight() * scaleFactor);

        Image imagenRedimensionada = imagen.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(imagenRedimensionada);
        this.PREVBOXLBL.setIcon(icono);
        this.PREVBOXLBL.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ApplyButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JLabel PREVBOXLBL;
    private javax.swing.JSlider SLIDER;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
