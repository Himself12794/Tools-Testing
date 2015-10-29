package com.pwhiting.collect;

import java.util.List;

/**
 * Convenient combination of {@link RangedCollection} and {@link List}
 * 
 * @author Philip
 *
 * @param <C>
 */
public interface RangedList<C extends Comparable<?>> extends RangedCollection<C>, List<C> {

}
