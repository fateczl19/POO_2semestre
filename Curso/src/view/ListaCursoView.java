package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.CursoController;
import model.dao.CursoDao;
import model.vo.Curso;

public class ListaCursoView extends JFrame{
	private JPanel painelFundo;
    private JPanel painelBotoes;
    private JTable tabela;
    private JScrollPane barraRolagem;
    private static CursoController control;
    private JButton btInserir;
    private JButton btExcluir;
    private JButton btEditar;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private DefaultTableModel modelo = new DefaultTableModel();
 
    public ListaCursoView(){
        super("Cursos");
        criaJTable();
        criaJanela();
    }
 
    public void criaJanela() {
        btInserir = new JButton("Inserir");
        btEditar = new JButton("Editar");
        btExcluir = new JButton("Excluir");
        painelBotoes = new JPanel();
        barraRolagem = new JScrollPane(tabela);
        painelFundo = new JPanel();
        painelFundo.setLayout(new BorderLayout());
        painelFundo.add(BorderLayout.CENTER, barraRolagem);
        painelBotoes.add(btInserir);
        painelBotoes.add(btEditar);
        painelBotoes.add(btExcluir);
        painelFundo.add(BorderLayout.SOUTH, painelBotoes);
 
        getContentPane().add(painelFundo);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 320);
        setVisible(true);
        btInserir.addActionListener(new BtInserirListener());
        btEditar.addActionListener(new BtEditarListener());
        btExcluir.addActionListener(new BtExcluirListener());
    }
 
    private void criaJTable() {
        tabela = new JTable(modelo);
        modelo.addColumn("Id");
        modelo.addColumn("Nome");
        modelo.addColumn("Descricao");
        modelo.addColumn("Ativo");
        modelo.addColumn("Horario Inicio");
        modelo.addColumn("Horario Termino");
        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(120);
        pesquisar(modelo);
    }
 
    public static void pesquisar(DefaultTableModel modelo) {
        modelo.setNumRows(0);
        control = new CursoController();
 
        for (Curso c : control.getCursos()) {
        	String ativo = (c.getAtivo() == 1) ? "Sim":"Não";
        	String inicio = sdf.format( c.getHorarioInicio());
        	String fim = sdf.format( c.getHorarioTermino());
        	
            modelo.addRow(new Object[]{c.getId(), c.getNome(), 
            c.getDescricao(), ativo, inicio, fim});
        }
    }
 
    private class BtInserirListener implements ActionListener {
 
        public void actionPerformed(ActionEvent e) {
        	CadastraCursoView ic = new CadastraCursoView(modelo);
        }
    }
 
    private class BtEditarListener implements ActionListener {
 
        public void actionPerformed(ActionEvent e) {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
            	long idCurso= (long) tabela
                .getValueAt(linhaSelecionada, 0);
                AtualizaCursoView ic = 
                new AtualizaCursoView(modelo, idCurso, linhaSelecionada);
            } else {
                JOptionPane.showMessageDialog(null, 
                "É necesário selecionar uma linha.");
            }
        }
    }
 
    private class BtExcluirListener implements ActionListener {
 
        public void actionPerformed(ActionEvent e) {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                long idContato = (long) tabela.getValueAt(linhaSelecionada, 0);
                CursoDao dao = new CursoDao();
                dao.remover(idContato);
                modelo.removeRow(linhaSelecionada);
            } else {
                JOptionPane.showMessageDialog(null, 
                "É necesário selecionar uma linha.");
            }
        }
    }
}
