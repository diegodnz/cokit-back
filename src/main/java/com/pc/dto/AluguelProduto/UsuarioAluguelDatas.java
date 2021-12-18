package com.pc.dto.AluguelProduto;

import com.pc.dto.Usuario.UsuarioLocatarioDto;
import com.pc.model.Usuario;

import java.time.LocalDate;
import java.util.List;

public class UsuarioAluguelDatas {

    private UsuarioLocatarioDto locador;

    List<IntervaloDatas> datas;

    public UsuarioAluguelDatas(UsuarioLocatarioDto locador, List<IntervaloDatas> datas) {
        this.locador = locador;
        this.datas = datas;
    }

    public UsuarioLocatarioDto getLocador() {
        return locador;
    }

    public void setLocador(UsuarioLocatarioDto locador) {
        this.locador = locador;
    }

    public List<IntervaloDatas> getDatas() {
        return datas;
    }

    public void setDatas(List<IntervaloDatas> datas) {
        this.datas = datas;
    }
}
