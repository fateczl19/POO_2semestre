package src.edu.ves.lojavirtual.view;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import src.edu.ves.lojavirtual.controller.ProdutoController;
import src.edu.ves.lojavirtual.model.Produto;

public class ProdutoView extends JPanel implements ProdutoController {
	private static final long serialVersionUID = 1L;
	private JLabel lblId;
	private JLabel lblNome;
	private JLabel lblPreco;
	private JLabel lblPeso;
	private JLabel lblValidade;
	private JLabel lblTipo;
	private JLabel lblDescricao;

	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtPreco;
	private JTextField txtPeso;
	private JTextField txtValidade;
	private JTextField txtTipo;
	private JTextField txtDescricao;

	private JButton btnInserir;
	private JButton btnAtualizar;
	private JButton btnRemover;
	private JButton btnPesquisar;
	private JButton btnRestaurar;

	private DefaultTableModel model;
	private JTable tblProdutos;

	private List<Produto> produtos;
	final String[] columns = new String[] { "ID", "Nome", "Preco", "Peso",
			"Validade", "Tipo", "Descricao" };

	public ProdutoView() {
		this.produtos = new ArrayList<>();

		this.lblId = new JLabel("ID:");
		this.txtId = new JTextField();

		this.lblNome = new JLabel("Nome:");
		this.txtNome = new JTextField();

		this.lblPreco = new JLabel("Preco:");
		this.txtPreco = new JTextField();

		this.lblPeso = new JLabel("Peso:");
		this.txtPeso = new JTextField();

		this.lblValidade = new JLabel("Validade:");
		try {
			this.txtValidade = new JFormattedTextField(
					new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.lblTipo = new JLabel("Tipo:");
		this.txtTipo = new JTextField();

		this.lblDescricao = new JLabel("Descricao:");
		this.txtDescricao = new JTextField();

		this.btnInserir = new JButton("Inserir");
		this.btnInserir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Produto p = construirProduto();
				adicionarProduto(p);
			}
		});

		this.btnAtualizar = new JButton("Atualizar");
		this.btnAtualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Produto p = construirProduto();
				if (p.getId() == 0) {
					return;
				}
				atualizarProduto(p.getId(), p);
			}
		});

		this.btnRemover = new JButton("Remover");
		this.btnRemover.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final long id = Long.valueOf(txtId.getText().trim());
				if (id == 0) {
					return;
				}
				removerProduto(Long.valueOf(id));
			}
		});

		this.btnPesquisar = new JButton("Pesquisar");
		this.btnPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final String nome = txtNome.getText().trim();
				if (nome.isEmpty()) {
					return;
				}
				for (Produto p : pesquisarProdutosPorNome(nome)) {
					inserirLinhaAoModel(p);
				}
			}
		});

		this.btnRestaurar = new JButton("Restaurar Tabela");
		this.btnRestaurar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restaurarModel();
			}
		});

		Box vBox = Box.createVerticalBox();

		Box vBoxLbl = Box.createVerticalBox();
		vBoxLbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 100));
		vBoxLbl.add(this.lblId);
		vBoxLbl.add(this.lblNome);
		vBoxLbl.add(this.lblPreco);
		vBoxLbl.add(this.lblPeso);
		vBoxLbl.add(this.lblValidade);
		vBoxLbl.add(this.lblTipo);
		vBoxLbl.add(this.lblDescricao);

		Box vBoxTxt = Box.createVerticalBox();
		vBoxTxt.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 10));
		vBoxTxt.add(this.txtId);
		vBoxTxt.add(this.txtNome);
		vBoxTxt.add(this.txtPreco);
		vBoxTxt.add(this.txtPeso);
		vBoxTxt.add(this.txtValidade);
		vBoxTxt.add(this.txtTipo);
		vBoxTxt.add(this.txtDescricao);

		Box hBoxBtn = Box.createHorizontalBox();
		hBoxBtn.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
		hBoxBtn.add(this.btnInserir);
		hBoxBtn.add(this.btnAtualizar);
		hBoxBtn.add(this.btnRemover);
		hBoxBtn.add(this.btnPesquisar);
		hBoxBtn.add(this.btnRestaurar);

		Box hBox = Box.createHorizontalBox();
		hBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		hBox.add(vBoxLbl);
		hBox.add(vBoxTxt);

		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public int getColumnCount() {
				return columns.length;
			}

			@Override
			public String getColumnName(int index) {
				return columns[index];
			}
		};

		tblProdutos = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(tblProdutos);
		tblProdutos.setVisible(true);
		Rectangle r = new Rectangle(this.getX() * 2, this.getY() * 2,
				getWidth(), 200);
		tblProdutos.setFillsViewportHeight(true);
		scrollPane.setBounds(r);

		vBox.add(hBox);
		vBox.add(hBoxBtn);
		vBox.add(scrollPane);

		add(vBox);
	}

	private Produto construirProduto() {
		Produto p = new Produto();
		p.setId(Long.parseLong(txtId.getText()));
		p.setNome(txtNome.getText());
		try {
			p.setPreco(Float.parseFloat(txtPreco.getText().replace(",", ".")));
			p.setPeso(Float.parseFloat(txtPeso.getText().replace(",", ".")));
			p.setValidade(new SimpleDateFormat("dd/MM/yyyy")
					.parse(txtValidade.getText()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		p.setTipo(txtTipo.getText());
		p.setDescricao(txtDescricao.getText());
		return p;
	}

	@Override
	public void adicionarProduto(Produto produto) {
		if (this.buscarIndexListaProdutos(produto.getId()) != -1) {
			return;
		}
		produtos.add(produto);
		inserirLinhaAoModel(produto);
	}

	@Override
	public void atualizarProduto(long id, Produto produto) {
		final int index = buscarIndexListaProdutos(id);
		removerProduto(id);
		produtos.add(index, produto);
		atualizarLinha(index, produto);
	}

	@Override
	public void removerProduto(long id) {
		final int index = this.buscarIndexListaProdutos(id);

		produtos.remove(index);
		restaurarModel();
	}

	@Override
	public List<Produto> pesquisarProdutosPorNome(String nome) {
		List<Produto> l = new ArrayList<>();
		for (Produto p : this.produtos) {
			if (p.getNome().toLowerCase().contains(nome.toLowerCase())) {
				l.add(p);
			}
		}
		limparModel();
		return l;
	}

	private void atualizarLinha(int index, Produto p) {
		model.insertRow(index, objRow(p));
	}

	private void inserirLinhaAoModel(Produto p) {
		model.addRow(objRow(p));
	}

	private Object[] objRow(Produto p) {
		return new Object[] { p.getId(), p.getNome(), p.getPreco(), p.getPeso(),
				new SimpleDateFormat("dd/MM/yyyy").format(p.getValidade()),
				p.getTipo(), p.getDescricao() };
	}

	private void limparModel() {
		while (model.getRowCount() > 0) {
			try {
				model.removeRow(0);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Linha nao disponivel.");
			}
		}
	}

	private void restaurarModel() {
		limparModel();
		for (Produto p : this.produtos) {
			inserirLinhaAoModel(p);
		}
	}

	private int buscarIndexListaProdutos(long id) {
		for (Produto p : this.produtos) {
			if (p.getId() == id) {
				int index = this.produtos.indexOf(p);
				return index;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		ProdutoView panel = new ProdutoView();
		JFrame frame = new JFrame("Produto");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

}
