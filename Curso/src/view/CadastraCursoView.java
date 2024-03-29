package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.dao.CursoDao;
import model.vo.Curso;

public class CadastraCursoView extends JFrame{
	private DefaultTableModel modelo = new DefaultTableModel();
	private JFrame janela;

	private JPanel contentPanel;
	private JPanel panelGridTop;
	private JPanel panelGridBottom;

	private BorderLayout boderLayout;
	private GridBagLayout gbLayout;
	
    private JPanel painelFundo;
    private JButton btSalvar;
    private JButton btLimpar;
	private JLabel lblNome;
	private JLabel lblDescricao;
	private JLabel lblAtivo;
	private JLabel lblHorarioInicio;
	private JLabel lblHorarioTermino;
	private JTextField txtNome;
	private JTextField txtDescricao;
	private JTextField txtHorarioInicio;
	private JTextField txtHorarioTermino;
	private JComboBox<Object> comboBoxAtivo;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
 
    public CadastraCursoView(DefaultTableModel md) {
        super("Cursos");
        criaJanela();
        modelo = md;
    }
 
    public void criaJanela() {
        btSalvar = new JButton("Salvar");
        btLimpar = new JButton("Limpar");
        
        lblNome = new JLabel("Nome:");
        lblDescricao = new JLabel("Descricao:");
        lblAtivo = new JLabel("Ativo:");
        lblHorarioInicio = new JLabel("Hor�rio Inicio:");
        lblHorarioTermino = new JLabel("Hor�rio Termino:");
        
        txtNome = new JTextField(15);
        txtDescricao = new JTextField(15);
        txtHorarioInicio = new JTextField(15);
        txtHorarioTermino = new JTextField(15);
        comboBoxAtivo = new JComboBox<>();
        
        
        comboBoxAtivo.addItem("Nao");
        comboBoxAtivo.addItem("Sim");

        btSalvar.addActionListener(new BtSalvarListener());
        btLimpar.addActionListener(new BtLimparListener());
        
        janela = new JFrame();
		contentPanel = new JPanel();
		panelGridTop = new JPanel();
		panelGridBottom = new JPanel();
		boderLayout = new BorderLayout();
		gbLayout = new GridBagLayout();

		panelGridTop.setLayout(gbLayout);
		panelGridBottom.setLayout(gbLayout);
		contentPanel.setLayout(boderLayout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gbc.anchor = 13;
		panelGridTop.add(lblNome, gbc);
		gbc.anchor = 17;
		panelGridTop.add(txtNome, gbc);
		
		gbc.gridy = 1;
		gbc.anchor = 13;
		panelGridTop.add(lblDescricao, gbc);
		gbc.anchor = 17;
		panelGridTop.add(txtDescricao, gbc);
		
		gbc.gridy = 2;
		gbc.anchor = 13;
		panelGridTop.add(lblAtivo, gbc);
		gbc.anchor = 17;
		panelGridTop.add(comboBoxAtivo, gbc);
		
		gbc.gridy = 3;
		gbc.anchor = 13;
		panelGridTop.add(lblHorarioInicio, gbc);
		gbc.anchor = 17;
		panelGridTop.add(txtHorarioInicio, gbc);
		
		gbc.gridy = 4;
		gbc.anchor = 13;
		panelGridTop.add(lblHorarioTermino, gbc);
		gbc.anchor = 17;
		panelGridTop.add(txtHorarioTermino, gbc);

		panelGridBottom.add(btSalvar, gbc);
		panelGridBottom.add(btLimpar, gbc);
		

		contentPanel.add(BorderLayout.NORTH, panelGridTop);
		contentPanel.add(BorderLayout.CENTER, panelGridBottom);

		janela.setContentPane(contentPanel);
		janela.setTitle("Cadastro Cursos");
		janela.setSize(450, 300);
		janela.setVisible(true);
		janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
 
    private class BtSalvarListener implements ActionListener {
 
        public void actionPerformed(ActionEvent e) {
        	Curso c = new Curso();
            c.setNome(txtNome.getText());
            c.setDescricao(txtDescricao.getText());
            Date inicio = null;
            Date fim = null;
			try {
				inicio = sdf.parse(txtHorarioInicio.getText());
				fim = sdf.parse(txtHorarioTermino.getText());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			c.setHorarioInicio(inicio);

			c.setHorarioTermino(fim);
            
            int ativo = comboBoxAtivo.getSelectedIndex();
            c.setAtivo(ativo);
 
            CursoDao dao = new CursoDao();
            dao.inserir(c);
            ListaCursoView.pesquisar(modelo);
 
            setVisible(false);
        }
    }
 
 
 
    private class BtLimparListener implements ActionListener {
 
        public void actionPerformed(ActionEvent e) {
        	txtNome.setText("");
            txtDescricao.setText("");;
            txtHorarioInicio.setText("");
            txtHorarioTermino.setText("");
        }
    }
}
