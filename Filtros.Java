package com.galeno.prepaga.commons.utils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Filtros para Listas
 *
 * @author eshin
 *
 */
public interface FiltrosPorCondicion {
	/**
	 * Utilizado para filtrar solo el PRIMER valor por algun metodo en
	 * particular
	 *
	 * @param Function
	 *            &lt? super T, ?&gt keyExtractor filtro a utilizar
	 *            CLASS::METHOD
	 * @return Predicate&ltT&gt condicion a filtrar
	 */
	public static <T> Predicate<T> filtroDistinctByMetodo(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	/**
	 * Obtiene un Stream&lt?&gt de los PRIMEROS(DISTINCT) valores filtrados por
	 * alguna condicion en particular
	 *
	 * @param list
	 *            Lista de objetos a filtrar
	 * @param condition
	 *            metodo getter por el cual filtrar
	 * @return Stream&lt?&gt
	 */
	public static <T> Stream<?> filtroUnicoPorCondicion(List<?> list, Function<? super T, ?> condition) {
		return list.stream().filter((Predicate) filtroDistinctByMetodo(condition));
	}

	/**
	 * Retorna una Function que cumpla con la condicion del keyExtractor
	 * utilizado como parametro
	 *
	 * @param Function
	 *            &lt? super T, ?&gt keyExtractor filtro a utilizar
	 *            CLASS::METHOD
	 * @return Function&ltT&gt condicion a filtrar
	 */
	public static <T> Function<T, ?> filtroPorAtributo(Function<? super T, ?> keyExtractor) {
		return t -> keyExtractor.apply(t);
	}

	/**
	 * Obtiene un Stream&lt?&gt de los valores filtrados por alguna condicion en
	 * particular
	 *
	 * @param list
	 *            list Lista de objetos a filtrar
	 * @param condition
	 *            condicion de filtrado CLASS::METHOD
	 * @return Stream&lt?&gt de la lista filtrada
	 */
	public static <T> Stream<?> mapeoPorCondicion(List<?> list, Function<? super T, ?> condition) {
		return list.stream().map((Function) condition);
	}

	/**
	 *  Just a basic Stream to List collector
	 */
	public static Function<Stream<?>, List<?>> toLista = strm -> strm.collect(Collectors.toList());
}
