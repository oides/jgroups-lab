package br.edu.ifba.gsort.inf623.distributeddatabase.persistence;

import javax.ejb.Stateless;

import br.edu.ifba.gsort.inf623.distributeddatabase.entity.Command;

@Stateless
public class CommandDAO extends GenericDaoJPAImpl<Command, Long> {
}
