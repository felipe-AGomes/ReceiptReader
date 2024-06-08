package com.felipeagomes.products;

public class Product {
    private String codigo;
    private String nome;
    private Double quantidade;
    private String unidade;
    private Double valor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        String sb = "{\n" + "\tcodigo: " +
                this.codigo + ",\n\tnome: " +
                this.nome + ",\n\tquantidade: " +
                this.quantidade + ",\n\tunidade: " +
                this.unidade + ",\n\tvalor: " +
                this.valor + "\n}";
        return sb;
    }
}
