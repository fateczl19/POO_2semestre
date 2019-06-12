package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

import controller.CursoController;
import model.dao.CursoDao;
import model.vo.Curso;

public class AtualizaCursoView extends JFrame{
	private DefaultTableModel modelo = new DefaultTableModel();
    private JButton btSalvar;
    private JButton btLimpar;
    
    private JLabel lblId;
    private JTextField txtId;
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
    Curso curso;
    private int linhaSelecionada;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private JFrame janela;
	private JPanel contentPanel;
	private JPanel panelGridTop;
	private JPanel panelGridBottom;
	private BorderLayout boderLayout;
	private GridBagLayout gbLayout;
 
    public AtualizaCursoView(DefaultTableModel md, long idCurso, int linha) {
        super("Cursos");
        criaJanela();
        modelo = md;
        CursoController control = new CursoController();
        curso = control.getCursoById(idCurso);
        txtId.setText(Integer.toString((int) curso.getId()));
        txtNome.setText(curso.getNome());
        txtDescricao.setText(curso.getDescricao());

        String inicio = sdf.format( curso.getHorarioInicio());
        txtHorarioInicio.setText(inicio);
        String fim = sdf.format( curso.getHorarioInicio());
        txtHorarioTermino.setText(fim);
        comboBoxAtivo.setSelectedIndex(curso.getAtivo());
        linhaSelecionada = linha;  
    }
 
    public void criaJanela() {
    	btSalvar = new JButton("Salvar");
        btLimpar = new JButton("Limpar");
        
        lblId = new JLabel("ID");
        lblNome = new JLabel("Nome:");
        lblDescricao = new JLabel("Descricao:");
        lblAtivo = new JLabel("Ativo:");
        lblHorarioInicio = new JLabel("Horário Inicio:");
        lblHorarioTermino = new JLabel("Horário Termino:");
        
        txtId = new JTextField(15);
        txtId.setEditable(false);
        
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
		panelGridTop.add(lblId, gbc);
		gbc.anchor = 17;
		panelGridTop.add(txtId, gbc);

		gbc.gridy = 1;
		gbc.anchor = 13;
		panelGridTop.add(lblNome, gbc);
		gbc.anchor = 17;
		panelGridTop.add(txtNome, gbc);
		
		gbc.gridy = 2;
		gbc.anchor = 13;
		panelGridTop.add(lblDescricao, gbc);
		gbc.anchor = 17;
		panelGridTop.add(txtDescricao, gbc);
		
		gbc.gridy = 3;
		gbc.anchor = 13;
		panelGridTop.add(lblAtivo, gbc);
		gbc.anchor = 17;
		panelGridTop.add(comboBoxAtivo, gbc);
		
		gbc.gridy = 4;
		gbc.anchor = 13;
		panelGridTop.add(lblHorarioInicio, gbc);
		gbc.anchor = 17;
		panelGridTop.add(txtHorarioInicio, gbc);
		
		gbc.gridy = 5;
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
	
        
        btSalvar.addActionListener(new AtualizaCursoView.BtSalvarListener());
        btLimpar.addActionListener(new AtualizaCursoView.BtLimparListener());
    }
 
    private class BtSalvarListener implements ActionListener {
 
        public void actionPerformed(ActionEvent e) {
            Curso c = new Curso();
            c.setId(Long.parseLong(txtId.getText()));
            c.setNome(txtNome.getText());
            c.setDescricao(txtDescricao.getText());
            Date inicio = null;
            Date fim = null;
			try {
				inicio = sdf.parse(txtHorarioInicio.getText());
				fim = sdf.parse(txtHorarioTermino.getText());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			c.setHorarioInicio(inicio);

			c.setHorarioTermino(fim);
            
            int ativo = comboBoxAtivo.getSelectedIndex();
            c.setAtivo(ativo); 
            CursoDao dao = new CursoDao();
            dao.atualizar(c);
            modelo.removeRow(linhaSelecionada);
            modelo.addRow(new Object[]{c.getId(), c.getNome(), 
                    c.getDescricao(), c.getAtivo(), c.getHorarioInicio(), c.getHorarioTermino()});
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