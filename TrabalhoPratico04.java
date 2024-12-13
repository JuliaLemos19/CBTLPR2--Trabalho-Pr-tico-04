 import javax.swing.*;
   import java.awt.*;
   import java.awt.event.*;
   import java.sql.*;

   public class TrabalhoPratico04 extends JFrame {
       private JTextField txtNome = new JTextField(20);
       private JTextField txtSalario = new JTextField(10);
       private JTextField txtCargo = new JTextField(20);
       private JButton btnPesquisar = new JButton("Pesquisar");
       private JButton btnAnterior = new JButton("Anterior");
       private JButton btnProximo = new JButton("Proximo");

       private Connection conexao;
       private ResultSet resultSet;

       public TrabalhoPratico04() {
           setTitle("Trabalho Pratico 04");
           setLayout(new FlowLayout());
           setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           setSize(400, 300);

           add(new JLabel("Nome:"));
           add(txtNome);
           add(btnPesquisar);

           add(new JLabel("Salario:"));
           add(txtSalario);
           txtSalario.setEditable(false);

           add(new JLabel("Cargo:"));
           add(txtCargo);
           txtCargo.setEditable(false);

           add(btnAnterior);
           add(btnProximo);

           btnPesquisar.addActionListener(e -> pesquisar());
           btnAnterior.addActionListener(e -> navegar(false));
           btnProximo.addActionListener(e -> navegar(true));

           conectarBanco();
       }

       private void conectarBanco() {
           try {
               conexao = DriverManager.getConnection("jdbc:sqlserver://"127.0.0.1\\SQLEXPRESS;databaseName=aulajava";);
           } catch (SQLException e) {
               JOptionPane.showMessageDialog(this, "Erro ao conectar no banco: " + e.getMessage());
           }
       }

       private void pesquisar() {
           try {
               String nome = txtNome.getText();
               PreparedStatement stmt = conexao.prepareStatement(
                   "SELECT f.nome_func, f.sal_func, c.ds_cargo FROM tbfuncs f " +
                   "JOIN tbcargos c ON f.cod_cargo = c.cd_cargo WHERE f.nome_func LIKE ?");
               stmt.setString(1, "%" + nome + "%");
               resultSet = stmt.executeQuery();

               if (resultSet.next()) {
                   atualizarCampos();
               } else {
                   JOptionPane.showMessageDialog(this, "Nenhum resultado encontrado.");
               }
           } catch (SQLException e) {
               JOptionPane.showMessageDialog(this, "Erro na pesquisa: " + e.getMessage());
           }
       }

       private void navegar(boolean proximo) {
           try {
               if ((proximo && resultSet.next()) || (!proximo && resultSet.previous())) {
                   atualizarCampos();
               } else {
                   JOptionPane.showMessageDialog(this, "Limite atingido.");
               }
           } catch (SQLException e) {
               JOptionPane.showMessageDialog(this, "Erro na navegação: " + e.getMessage());
           }
       }

       private void atualizarCampos() {
           try {
               txtNome.setText(resultSet.getString("nome_func"));
               txtSalario.setText(resultSet.getString("sal_func"));
               txtCargo.setText(resultSet.getString("ds_cargo"));
           } catch (SQLException e) {
               JOptionPane.showMessageDialog(this, "Erro ao atualizar campos: " + e.getMessage());
           }
       }

       public static void main(String[] args) {
           SwingUtilities.invokeLater(() -> {
               new TrabalhoPratico04().setVisible(true);
           });
       }
   }