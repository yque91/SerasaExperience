/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.serasa.telas;

import java.sql.*;
import br.com.serasa.dal.ModuloConexao;
import java.text.NumberFormat;
import javax.swing.JOptionPane;
// a linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author chsfi
 */
public class TelaEmpresa extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    

    /**
     * Creates new form TelaCliente
     */
    public TelaEmpresa() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    public void adicionar() {
        //metodo para adicionar CLIENTES
        String sql = "insert into empresa(empresa, cnpj, endereco, telefone, email) values (?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEmpNome.getText());
            pst.setString(2, txtEmpCNPJ.getText());
            pst.setString(3, txtEmpEnd.getText());
            pst.setString(4, txtEmpTel.getText());
            pst.setString(5, txtEmpEmail.getText());
            // validacao dos campos obrigatorios
            if ((txtEmpNome.getText().isEmpty()) || (txtEmpCNPJ.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                //a linha abaixo atualiza a tabela usuarios com os dados do formulario
                // a esttrutura abaixo confirma a inserção na tabela
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Empresa adicionada com sucesso");
                    txtEmpNome.setText(null);
                    txtEmpEnd.setText(null);
                    txtEmpTel.setText(null);
                    txtEmpEmail.setText(null);
                    txtEmpCNPJ.setText(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo para alterar dados do cliente
    public void alterar() {
        //metodo para alterar
        String sql = "UPDATE empresa SET empresa = ?, cnpj = ?, "
                + "endereco = ?, telefone = ?, email = ? WHERE (id = ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEmpNome.getText());
            pst.setString(2, txtEmpCNPJ.getText());
            pst.setString(3, txtEmpEnd.getText());
            pst.setString(4, txtEmpTel.getText());
            pst.setString(5, txtEmpEmail.getText());
            pst.setString(6, txtEmpId.getText());
            if ((txtEmpNome.getText().isEmpty()) || (txtEmpCNPJ.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                //a linha abaixo atualiza a tabela usuarios com os dados do formulario
                // a esttrutura abaixo confirma a ALTERACAO dos dados na tabela
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente alterado com sucesso");
                    txtEmpNome.setText(null);
                    txtEmpEnd.setText(null);
                    txtEmpTel.setText(null);
                    txtEmpEmail.setText(null);
                    txtEmpId.setText(null);
                    txtEmpCNPJ.setText(null);
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void remover() { //metodo responsavel pela remocao de usuarios e precisa de confirmacao
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from empresa where id=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtEmpId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Empresa removida com sucesso!");
                    txtEmpId.setText(null);
                    txtEmpNome.setText(null);
                    txtEmpTel.setText(null);
                    txtEmpEmail.setText(null);
                    txtEmpCNPJ.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    //metodo para pesquisar clientes pelo nome com filtro
    private void pesquisar_cliente() {
        String sql = "select id, empresa, cnpj, endereco, telefone, email from empresa where empresa like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteudo para a caixa de pesquisa para o ?
            // atenção ao ? q é a continuacao da string SQL
            pst.setString(1, txtEmpPesquisar.getText() + "%");
            rs = pst.executeQuery();
            // a linha abaixo usa a biblioteca rs2xml.jar p preencher a TABELA
            tblEmpresas.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo para setar os campos do formulario com o conteudo da tabela
    public void setar_campos() {
        int setar = tblEmpresas.getSelectedRow();
        txtEmpId.setText(tblEmpresas.getModel().getValueAt(setar, 0).toString());
        txtEmpNome.setText(tblEmpresas.getModel().getValueAt(setar, 1).toString());
        txtEmpCNPJ.setText(tblEmpresas.getModel().getValueAt(setar, 2).toString());
        txtEmpEnd.setText(tblEmpresas.getModel().getValueAt(setar, 3).toString());
        txtEmpTel.setText(tblEmpresas.getModel().getValueAt(setar, 4).toString());
        txtEmpEmail.setText(tblEmpresas.getModel().getValueAt(setar, 5).toString());

        // a linha abaixo desabilita o botao add
        btnAdicionar.setEnabled(false);
    }

    public void qualificação() {

        //pegar o numero total de créditos e o numero total de débitos de uma determinada empresa
        //depois fazer a conta
        String sqlcre = "select count(*) from contratos where tipo='Credito' and idempresa=?";
        String sqldeb = "select count(*) from contratos where tipo='Debito' and idempresa=?";
        try {
            pst = conexao.prepareStatement(sqlcre);
            pst.setString(1, txtEmpId.getText());
            //TENTANDO AQUI PEGAR O VALOR DO COUNT
            ResultSet rs = pst.executeQuery();
            rs.next();
            lblTotNf.setText(rs.getString(1));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            pst = conexao.prepareStatement(sqldeb);
            pst.setString(1, txtEmpId.getText());
            //TENTANDO AQUI PEGAR O VALOR DO COUNT
            ResultSet rs = pst.executeQuery();
            rs.next();
            lblTotDeb.setText(rs.getString(1));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void confiabilidade () {   
        String contacred = lblTotNf.getText(); // pega o valor do credito
        String contadeb = lblTotDeb.getText(); //pega o valor do deb
        //String conta = lblPontos.getText(); 
        int credito = Integer.parseInt(contacred); //converte pra int
        int debito = Integer.parseInt(contadeb); // converte pra int
        double valor = 50;
        while (credito > 0) {
            valor = valor + valor*0.02;
            credito--;
        }
        while (debito > 0) {
            valor = valor - valor*0.04;
            debito--;
        }
            NumberFormat df1 = NumberFormat.getNumberInstance();
            df1.setMaximumFractionDigits(2);
            df1.setMinimumFractionDigits(2);
            lblPontos.setText(df1.format(valor));
            
    }
    
    private void atualiza_pontos() {
         String x = lblPontos.getText().replaceAll(",",".");
         String sql = "UPDATE empresa SET pontos = ? WHERE (id = ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, x);
            pst.setString(2, txtEmpId.getText());
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        txtEmpNome = new javax.swing.JTextField();
        txtEmpEnd = new javax.swing.JTextField();
        txtEmpTel = new javax.swing.JTextField();
        txtEmpEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEmpPesquisar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpresas = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtEmpId = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtEmpCNPJ = new javax.swing.JTextField();
        btnQuali = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblTotNf = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblTotDeb = new javax.swing.JLabel();
        lblPontos = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Empresa");

        jLabel1.setText("* Nome:");

        jLabel2.setText("Endereco:");

        jLabel3.setText(" Telefone:");

        jLabel4.setText("E-Mail:");

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/serasa/icones/create (1).png"))); // NOI18N
        btnAdicionar.setToolTipText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/serasa/icones/uptade.png"))); // NOI18N
        btnAlterar.setToolTipText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/serasa/icones/delete.png"))); // NOI18N
        btnRemover.setToolTipText("Deletar");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        txtEmpNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpNomeActionPerformed(evt);
            }
        });

        jLabel5.setText("* Campos obrigatórios");

        txtEmpPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpPesquisarActionPerformed(evt);
            }
        });
        txtEmpPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmpPesquisarKeyReleased(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/serasa/icones/iconfinder.png"))); // NOI18N

        tblEmpresas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEmpresas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblEmpresas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmpresasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEmpresas);

        jLabel7.setText("ID Empresa");

        txtEmpId.setEnabled(false);
        txtEmpId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpIdActionPerformed(evt);
            }
        });

        jLabel8.setText("*CNPJ:");

        txtEmpCNPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpCNPJActionPerformed(evt);
            }
        });

        btnQuali.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnQuali.setText("Confiabilidade da empresa selecionada");
        btnQuali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQualiActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setText("%");

        jLabel10.setText("Total NF");

        lblTotNf.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotNf.setText("0");

        jLabel12.setText("Total Debito");

        lblTotDeb.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotDeb.setText("0");

        lblPontos.setEditable(false);
        lblPontos.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblPontos.setText("50");
        lblPontos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblPontosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmpId, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmpNome)
                                    .addComponent(txtEmpEnd)
                                    .addComponent(txtEmpTel)
                                    .addComponent(txtEmpEmail)
                                    .addComponent(txtEmpCNPJ)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(87, 87, 87)
                                        .addComponent(btnQuali)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblPontos, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(btnAdicionar)
                                .addGap(31, 31, 31)
                                .addComponent(btnAlterar)
                                .addGap(33, 33, 33)
                                .addComponent(btnRemover)
                                .addGap(105, 105, 105))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtEmpPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(185, 185, 185)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(lblTotNf))
                                .addGap(91, 91, 91)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTotDeb)
                                    .addComponent(jLabel12))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmpPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuali)
                    .addComponent(lblPontos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotNf)
                    .addComponent(lblTotDeb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtEmpId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmpNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtEmpCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmpEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmpTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmpEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRemover, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(77, 77, 77))
        );

        setBounds(0, 0, 616, 609);
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmpNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmpNomeActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        adicionar();        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void txtEmpPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmpPesquisarActionPerformed

    private void txtEmpPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpPesquisarKeyReleased
        // esse evento eh pra enquanto tiver digitando
        pesquisar_cliente();
    }//GEN-LAST:event_txtEmpPesquisarKeyReleased

    private void tblEmpresasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpresasMouseClicked
        //eevento q sera usado para setar os campos da tabela clicando com o mouse    
        setar_campos();
    }//GEN-LAST:event_tblEmpresasMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        //chamando o metodo para alterar 
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void txtEmpCNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpCNPJActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmpCNPJActionPerformed

    private void btnQualiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQualiActionPerformed
        qualificação();// TODO add your handling code here:
        confiabilidade();
        atualiza_pontos();
    }//GEN-LAST:event_btnQualiActionPerformed

    private void lblPontosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblPontosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPontosActionPerformed

    private void txtEmpIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmpIdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnQuali;
    private javax.swing.JButton btnRemover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lblPontos;
    private javax.swing.JLabel lblTotDeb;
    private javax.swing.JLabel lblTotNf;
    private javax.swing.JTable tblEmpresas;
    private javax.swing.JTextField txtEmpCNPJ;
    private javax.swing.JTextField txtEmpEmail;
    private javax.swing.JTextField txtEmpEnd;
    private javax.swing.JTextField txtEmpId;
    private javax.swing.JTextField txtEmpNome;
    private javax.swing.JTextField txtEmpPesquisar;
    private javax.swing.JTextField txtEmpTel;
    // End of variables declaration//GEN-END:variables
}
