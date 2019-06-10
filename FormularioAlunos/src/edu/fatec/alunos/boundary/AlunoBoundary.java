package edu.fatec.alunos.boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import edu.fatec.alunos.control.AlunoControl;
import edu.fatec.alunos.control.ControlException;
import edu.fatec.alunos.entity.Aluno;

public class AlunoBoundary extends JFrame implements ActionListener {

	private JButton btnInserir = new JButton("Inserir");
	private JButton btnAlterar = new JButton("Alterar");
	private JButton btnRemover = new JButton("Remover");
	private JButton btnPesquisar = new JButton("Pesquisar");
	private JButton btnLimpar = new JButton("Limpar");
	private JPanel contentPane = new JPanel();;
	private JLabel lblNome = new JLabel("Nome");
	private JLabel lblRa = new JLabel("RA");
	private JLabel lblIdade = new JLabel("Idade");
	private JLabel lblNascimento = new JLabel("Nascimento");
	private JTable tabelaAlunos = new JTable();
	private JScrollPane scrollPane = new JScrollPane(tabelaAlunos);
	private JTextField txtNascimento = new JTextField();
	private JTextField txtIdade = new JTextField();
	private JTextField txtNome = new JTextField();
	private JTextField txtRA = new JTextField();
	private DefaultTableModel model = new DefaultTableModel();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private AlunoControl control = new AlunoControl();

	public AlunoBoundary() {
		setResizable(false);
		setTitle("Formulário de Alunos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		tabelaAlunos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableToTxt();
			}
		});

		tabelaAlunos.setModel(model);
		atualizarTabela("");

		lblNome.setBounds(283, 10, 40, 25);
		lblRa.setBounds(17, 10, 18, 25);
		lblIdade.setBounds(12, 47, 40, 25);
		lblNascimento.setBounds(283, 47, 85, 25);

		contentPane.add(lblNome);
		contentPane.add(lblRa);
		contentPane.add(lblIdade);
		contentPane.add(lblNascimento);

		txtNascimento.setBounds(386, 47, 257, 25);
		txtIdade.setBounds(115, 47, 150, 25);
		txtNome.setBounds(341, 10, 428, 25);
		txtRA.setBounds(115, 13, 150, 25);

		contentPane.add(txtNascimento);
		contentPane.add(txtIdade);
		contentPane.add(txtNome);
		contentPane.add(txtRA);

		scrollPane.setBounds(17, 84, 769, 436);
		contentPane.add(scrollPane);

		btnInserir.setBounds(294, 532, 114, 25);
		btnAlterar.setBounds(420, 532, 114, 25);
		btnRemover.setBounds(546, 532, 114, 25);
		btnPesquisar.setBounds(672, 532, 114, 25);
		btnLimpar.setBounds(655, 47, 114, 25);

		btnInserir.setActionCommand("inserir");
		btnAlterar.setActionCommand("alterar");
		btnRemover.setActionCommand("remover");
		btnPesquisar.setActionCommand("pesquisar");
		btnLimpar.setActionCommand("limpar");

		btnInserir.addActionListener(this);
		btnAlterar.addActionListener(this);
		btnRemover.addActionListener(this);
		btnPesquisar.addActionListener(this);
		btnLimpar.addActionListener(this);

		contentPane.add(btnInserir);
		contentPane.add(btnAlterar);
		contentPane.add(btnRemover);
		contentPane.add(btnPesquisar);
		contentPane.add(btnLimpar);

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String opcao = evt.getActionCommand();
		if (verificaCampos() == true) {
			if (opcao.equals("inserir")) {
				Aluno a = boundaryToAluno();
				try {
					control.inserir(a);
				} catch (ControlException e) {
					e.printStackTrace();
					dialog("Erro ao inserir aluno");
				}
				atualizarTabela("");
			} else if (opcao.equals("alterar")) {
				Aluno a = boundaryToAluno();
				try {
					control.aualizar(a);
				} catch (ControlException e) {
					e.printStackTrace();
					dialog("Erro ao atualizar dados");
				}
				atualizarTabela("");
			} else if (opcao.equals("remover")) {
				Aluno a = boundaryToAluno();
				try {
					control.remover(a);
				} catch (ControlException e) {
					e.printStackTrace();
					dialog("Erro ao remover aluno");
				}
				atualizarTabela("");
			} else if (opcao.equals("pesquisar")) {
				try {

					control.pesquisar(txtRA.getText());
				} catch (ControlException e) {
					e.printStackTrace();
					dialog("Erro ao pesquisar aluno");
				}
				atualizarTabela(txtRA.getText());
			} else if (opcao.equals("limpar")) {
				limparCampos();
			}
			limparCampos();
		}
	}

	private void dialog(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
	}

	private void tableToTxt() {
		int linhaSelecionada = tabelaAlunos.getSelectedRow();
		txtRA.setText(model.getValueAt(linhaSelecionada, 0).toString());
		txtNome.setText(model.getValueAt(linhaSelecionada, 1).toString());
		txtIdade.setText(model.getValueAt(linhaSelecionada, 2).toString());
		txtNascimento.setText(model.getValueAt(linhaSelecionada, 3).toString());
	}

	private Aluno boundaryToAluno() {
		Aluno a = new Aluno();
		a.setRa(txtRA.getText());
		a.setNome(txtNome.getText());
		a.setIdade(Integer.parseInt(txtIdade.getText()));
		try {
			Date d = dateFormat.parse(txtNascimento.getText());
			a.setNascimento(d);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return a;
	}

	private boolean verificaCampos() {
		if (txtRA.getText().trim().length() < 1) {
			dialog("RA vazio");
			txtRA.requestFocus();
			return false;
		} else if (txtNome.getText().trim().length() < 1) {
			dialog("Nome vazio");
			txtNome.requestFocus();
			return false;
		} else if (txtIdade.getText().trim().length() < 1) {
			dialog("Idade vazia");
			txtIdade.requestFocus();
			return false;
		} else if (txtNascimento.getText().trim().length() < 1) {
			dialog("Data de nascimento vazio");
			txtNascimento.requestFocus();
			return false;
		}
		return true;
	}

	private void limparCampos() {
		txtRA.setText("");
		txtNome.setText("");
		txtIdade.setText("");
		txtNascimento.setText("");
		txtRA.requestFocus();
	}

	private void atualizarTabela(String queryParam) {
		List<Aluno> alunos = new ArrayList<>();
		String[] colunas = { "RA", "Nome", "Idade", "Nascimento" };
		model.setColumnIdentifiers(colunas);
		model.setRowCount(0);
		try {
			control.pesquisar(queryParam);
			alunos = control.getAlunos();
			for (int i = 0; i < alunos.size(); i++) {
				String ra = alunos.get(i).getRa();
				String nome = alunos.get(i).getNome();
				String idade = String.valueOf(alunos.get(i).getIdade());
				String nascimento = dateFormat.format(alunos.get(i).getNascimento());
				model.addRow(new Object[] { ra, nome, idade, nascimento });
			}
			alunos.clear();
		} catch (ControlException e) {
			e.printStackTrace();
		}
	}
}