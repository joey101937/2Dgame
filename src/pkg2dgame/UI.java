/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import GameObjects.ID;
import Units.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * UI sidepanel that contains buttons for production and commands. Displays
 * selected unit name/stats and player resources
 *
 * @author Joseph
 */
public class UI extends javax.swing.JPanel implements Runnable {

    /**
     * Creates new form UI
     */
    ///fields
    Game myGame;        //the game
    User owner = Game.localUser;         //the user this goes with
    public GameObject selected;    //the currently selected game object

    public UI(Game myGame) {
        initComponents();
        this.myGame = myGame;
        this.setVisible(true);
        this.setOpaque(true);
        this.MetalLabel.setIcon(new ImageIcon(Main.getDir() + Main.assets + "MetalSprite.png"));
        this.OilLabel.setIcon(new ImageIcon(Main.getDir() + Main.assets + "OilResSprite.png"));
//          this.MetalLabel.setIcon(new ImageIcon(new Sprite(getClass().getResource("/Assets/MetalSprite.png")).url));
        //   this.OilLabel.setIcon(new ImageIcon(new Sprite(getClass().getResource("/Assets/OilPumpSprite.png")).url));

        this.clear();
    }

    public void tick() {
        
        try {

            this.MetalAmount.setText(new Integer(owner.metal).toString());
            this.OilAmount.setText(new Integer(owner.oil).toString());
            if (Game.ki.selected.size() > 0) {
                selected = Game.ki.selected.get(0);//if we have something selected, dispalay the first one
                if(selected == null) return;
               this.CurrentlySelectedText.setText("Currently Selected: " + selected.name);
                this.PictureLabel.setIcon(new ImageIcon(selected.spriteIcon[0].toString()));
                if (selected.id == ID.Unit) { //if the selected thing is a unit
                    Unit u = (Unit) selected;
                    this.PictureLabel.setToolTipText(selected.desc);
                    this.UnitTypeText.setText(u.unitTypes.toString());
                    this.HPdisplay.setText("HP: " + u.health + "/" + u.maxHealth);
                    this.labelProduction.setText("Commands");

                    if (u.unitTypes.contains(Type.builder)) {
                        //its a stucture
                        this.labelProduction.setText("Production");
                        Builder s = (Builder) u;
                        ///buttons for structures
                        this.setProductionButtons(s);
                    } else {
                        ///buttons for Units
                        this.product1Button.setIcon(new ImageIcon(Main.getDir() + Main.assets + "stopCommand.png")); ////stopCommand image when selecting a unit
                        this.product1Button.setToolTipText("Force stops movement");
                        this.production1Label.setText("Stop (X)");
                        this.product2Button.setIcon(new ImageIcon(Main.getDir() + Main.assets + "AMoveSprite.png"));
                        this.product2Button.setToolTipText("Moves to a point, attacking all in its way");
                        this.production2Label.setText("Attack-Move (Q)");
                        this.product3Button.setIcon(new ImageIcon(Main.getDir() + Main.assets + "attackCommand.png"));
                        this.product3Button.setToolTipText("Manually target unit");
                        this.production3Label.setText("Manual Target (Z)");
                    }
                }

            } else {
                this.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * resets the selection pane to none
     */
    public void clear() {
        this.CurrentlySelectedText.setText("Currently Selected: none");
        this.PictureLabel.setIcon(null);
        this.UnitTypeText.setText("");
        this.HPdisplay.setText("");
        this.product1Button.setIcon(null);
        this.product2Button.setIcon(null);
        this.product3Button.setIcon(null);
        this.production1Label.setText("");
        this.production2Label.setText("");
        this.production3Label.setText("");
        this.production1Label.setToolTipText("");
        this.production2Label.setToolTipText("");
        this.production3Label.setToolTipText("");
    }

    /**
     * sets the buttons for each of the Building's products if it is null, it is
     * defaulted to the stop command button 3 not yet implemented
     * @param s
     */
    public void setProductionButtons(Builder s) {
        if (s.getProduct(0) != null) {
            this.product1Button.setIcon(new ImageIcon(s.getProduct(0).spriteIcon[0].toString()));
            this.production1Label.setText(s.getProduct(0).name + ": " + "Cost: " + s.getProduct(0).mCost + " , " + s.getProduct(0).oCost);
            this.product1Button.setToolTipText(s.getProduct(0).desc);
        } else {
            this.product1Button.setIcon(new ImageIcon(Main.getDir() + Main.assets + "stopCommand.png")); ////stopCommand image when selecting a unit
            this.product1Button.setToolTipText("Force stops movement");
            this.production1Label.setText("Stop (X)");
        }
        if (s.getProduct(1) != null) {
            this.product2Button.setIcon(new ImageIcon(s.getProduct(1).spriteIcon[1].toString()));
            this.production2Label.setText(s.getProduct(1).name + ": " +"Cost: " + s.getProduct(1).mCost + " , " + s.getProduct(1).oCost);
            this.product2Button.setToolTipText(s.getProduct(1).desc);
        } else {
            this.product2Button.setIcon(new ImageIcon(Main.getDir() + Main.assets + "stopCommand.png")); ////stopCommand image when selecting a unit
            this.product2Button.setToolTipText("Force stops movement");
            this.production2Label.setText("Stop (X)");
        }
        if (s.getProduct(2) != null) {
            this.product3Button.setIcon(new ImageIcon(s.getProduct(2).spriteIcon[2].toString()));
            this.production3Label.setText(s.getProduct(2).name + ": " +"Cost: " + s.getProduct(2).mCost + " , " + s.getProduct(2).oCost);
            this.product3Button.setToolTipText(s.getProduct(2).desc);
        } else {
            this.product3Button.setIcon(new ImageIcon(Main.getDir() + Main.assets + "stopCommand.png")); ////stopCommand image when selecting a unit
            this.product3Button.setToolTipText("Force stops movement");
            this.production3Label.setText("Stop (X)");
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

        MetalLabel = new javax.swing.JLabel();
        OilLabel = new javax.swing.JLabel();
        MetalAmount = new javax.swing.JLabel();
        OilAmount = new javax.swing.JLabel();
        PictureLabel = new javax.swing.JLabel();
        CurrentlySelectedText = new javax.swing.JLabel();
        UnitTypeText = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        HPdisplay = new javax.swing.JLabel();
        product1Button = new javax.swing.JButton();
        labelProduction = new javax.swing.JLabel();
        production1Label = new javax.swing.JLabel();
        product2Button = new javax.swing.JButton();
        production2Label = new javax.swing.JLabel();
        product3Button = new javax.swing.JButton();
        production3Label = new javax.swing.JLabel();

        setBackground(new java.awt.Color(153, 153, 153));
        setPreferredSize(new java.awt.Dimension(200, 508));

        MetalLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        MetalLabel.setText("Metal:");

        OilLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        OilLabel.setText("Oil:");

        MetalAmount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        MetalAmount.setText("123");

        OilAmount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        OilAmount.setText("123");

        PictureLabel.setBackground(new java.awt.Color(204, 204, 204));

        CurrentlySelectedText.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        CurrentlySelectedText.setText("CURRENTLY SELECTED: None");

        UnitTypeText.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        UnitTypeText.setText("<unit type>");

        HPdisplay.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        HPdisplay.setText("HP: X/X");

        product1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product1ButtonActionPerformed(evt);
            }
        });

        labelProduction.setText("PRODUCTION");

        production1Label.setText("<desc>");

        product2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product2ButtonActionPerformed(evt);
            }
        });

        production2Label.setText("<desc>");

        product3Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product3ButtonActionPerformed(evt);
            }
        });

        production3Label.setText("<desc>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelProduction, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(HPdisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))
                            .addComponent(UnitTypeText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(OilLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(OilAmount))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(MetalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MetalAmount))
                            .addComponent(product1Button, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(product3Button, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(production3Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(CurrentlySelectedText, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(product2Button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(production1Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(production2Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MetalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MetalAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(OilAmount)
                    .addComponent(OilLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(CurrentlySelectedText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(UnitTypeText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HPdisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(PictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelProduction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(product1Button, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(production1Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(product2Button, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(production2Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(product3Button, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(production3Label)
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void product1ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product1ButtonActionPerformed
        try {
            switch (this.selected.name) {
                case "Tank":
                case "Boat":
                case "Helicopter":
                case "AutoTurret":
                    Unit u = (Unit) selected;
                    u.issueStopCommand();
                    return; ///if its a tank, do nothing
                case "Field Truck":
                case "Factory":
                    Builder bf = (Builder) selected;// case our unit as a Structure
                    bf.Produce1();
                    break;

                default:
                    return; //we dont do anyting if we dont know what it is
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_product1ButtonActionPerformed

    private void product2ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product2ButtonActionPerformed
        try {
            switch (this.selected.name) {
                case "Tank":
                case "Boat":
                case "Helicopter":
                case "AutoTurret":
                    Game.isAdown = true;
                    return; ///if its a tank, do nothing
                case "Field Truck":
                case "Factory":
                    Builder bf = (Builder) selected;// case our unit as a Structure
                    bf.Produce2();
                    break;
                default:
                    return; //we dont do anyting if we dont know what it is
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_product2ButtonActionPerformed
    /*
    private void product2ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product3ButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_product3ButtonActionPerformed
*/
    private void product3ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product2Button1ActionPerformed
        try {
            switch (this.selected.name) {
                case "Tank":
                case "Boat":
                case "Helicopter":
                case "AutoTurret":
                    Unit u = (Unit) selected;
                    Game.lookingForTarget = true;
                    return; ///if its a combat unit, this is manual fire mode
                case "Field Truck":
                case "Factory":
                    Builder bf = (Builder) selected;// case our unit as a Structure
                    bf.Produce3();
                    break;
                default:
                    return; //we dont do anyting if we dont know what it is
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_product2Button1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CurrentlySelectedText;
    private javax.swing.JLabel HPdisplay;
    private javax.swing.JLabel MetalAmount;
    private javax.swing.JLabel MetalLabel;
    private javax.swing.JLabel OilAmount;
    private javax.swing.JLabel OilLabel;
    private javax.swing.JLabel PictureLabel;
    private javax.swing.JLabel UnitTypeText;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelProduction;
    private javax.swing.JButton product1Button;
    private javax.swing.JButton product2Button;
    private javax.swing.JButton product3Button;
    private javax.swing.JLabel production1Label;
    private javax.swing.JLabel production2Label;
    private javax.swing.JLabel production3Label;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        try {
            Thread.sleep(100);
            this.tick();
        } catch (InterruptedException ex) {
        }
    }

}
