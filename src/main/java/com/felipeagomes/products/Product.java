package com.felipeagomes.products;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String codigo;
    private String nome;
    private String quantidade;
    private String unidade;
    private String valor;

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

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
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

    public List<String> getColumnNames() {
        List<String> columns = new ArrayList<>();

        columns.add("codigo");
        columns.add("nome");
        columns.add("quantidade");
        columns.add("valor");
        columns.add("unidade");

        return columns;
    }

    public String getPosition(int index) {
        return switch (index) {
            case 0 -> getCodigo();
            case 1 -> getNome();
            case 2 -> getQuantidade();
            case 3 -> getValor();
            case 4 -> getUnidade();
            default -> throw new IllegalArgumentException("Invalid index: " + index);
        };
    }
}
