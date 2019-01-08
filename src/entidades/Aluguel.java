package entidades;

import java.util.Date;
import java.util.List;

public class Aluguel {
    private Integer idAluguel;
    private List<Filme> filmes;
    private Cliente cliente;
    private Date dataAluguel;
    private float valor;

    public Aluguel() {
    }

    public Aluguel(Integer idAluguel, Cliente cliente, Date dataAluguel, float valor) {
        this.idAluguel = idAluguel;
        this.cliente = cliente;
        this.dataAluguel = dataAluguel;
        this.valor = valor;
    }

    public Integer getIdAluguel() {
        return idAluguel;
    }

    public Aluguel setIdAluguel(Integer idAluguel) {
        this.idAluguel = idAluguel;
        return this;
    }

    public Filme getFilme(int posicao) {
        return filmes.get(posicao);
    }

    public void setFilme(Filme filme) {
        this.filmes.add(filme);
    }
    
    public List<Filme> getFilmes() {
		return filmes;
	}

	public Aluguel setFilmes(List<Filme> filmes) {
		this.filmes = filmes;
		return this;
	}

	public Cliente getCliente() {
        return cliente;
    }

    public Aluguel setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public Date getDataAluguel() {
        return dataAluguel;
    }

    public Aluguel setDataAluguel(Date dataAluguel) {
        this.dataAluguel = dataAluguel;
        return this;
    }

    public float getValor() {
        return valor;
    }

    public Aluguel setValor(float valor) {
        this.valor = valor;
        return this;
    }

	@Override
	public String toString() {
		return "Aluguel [idAluguel= " + idAluguel + ", cliente= " + cliente.getNome() + ", dataAluguel= " + dataAluguel + ", valor= "
				+ valor + "]";
	}
    
    
}
