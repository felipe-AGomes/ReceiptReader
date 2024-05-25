package com.felipeagomes.products;

import java.util.ArrayList;
import java.util.List;

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
        StringBuilder sb = new StringBuilder();
        sb
                .append("{\n").append("\tcodigo: ")
                .append(this.codigo).append(",\n\tnome: ")
                .append(this.nome).append(",\n\tquantidade: ")
                .append(this.quantidade).append(",\n\tunidade: ")
                .append(this.unidade).append(",\n\tvalor: ")
                .append(this.valor).append("\n}");
        return sb.toString();
    }

    public Object getPosition(ProductColumn column) {
        return switch (column) {
            case CODIGO -> getCodigo();
            case NOME -> getNome();
            case QUANTIDADE -> getQuantidade();
            case VALOR -> getValor();
            case UNIDADE -> getUnidade();
            default -> throw new IllegalArgumentException("Invalid column: " + column);
        };
    }
}
