package com.oracle.coherence.spring.data.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Stream;

import com.oracle.coherence.repository.AbstractRepository;
import com.oracle.coherence.repository.EntityFactory;
import com.tangosol.util.Filter;
import com.tangosol.util.Fragment;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.ValueUpdater;
import com.tangosol.util.function.Remote;
import com.tangosol.util.stream.RemoteCollector;
import com.tangosol.util.stream.RemoteStream;

import org.springframework.data.repository.CrudRepository;

@SuppressWarnings({"checkstyle:JavadocStyle", "CheckStyle"})
public interface CoherenceRepository<T, ID> extends CrudRepository<T, ID> {

	@Override
	<S extends T> S save(S entity);

	@Override
	<S extends T> Iterable<S> saveAll(Iterable<S> entities);

	@Override
	Optional<T> findById(ID id);

	@Override
	boolean existsById(ID id);

	@Override
	Iterable<T> findAll();

	@Override
	Iterable<T> findAllById(Iterable<ID> ids);

	@Override
	long count();

	@Override
	void deleteById(ID id);

	@Override
	void delete(T entity);

	@Override
	void deleteAll(Iterable<? extends T> entities);

	@Override
	void deleteAll();

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#findAll(Filter)
	 */
	Collection<T> findAll(Filter<?> filter);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#findAll(ValueExtractor)
	 */
	<R extends Comparable<? super R>> Collection<T> findAll(ValueExtractor<? super T, ? extends R> orderBy);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#findAll(Filter, ValueExtractor)
	 */
	<R extends Comparable<? super R>> Collection<T> findAll(Filter<?> filter, ValueExtractor<? super T, ? extends R> orderBy);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#findAll(Remote.Comparator)
	 */
	Collection<T> findAll(Remote.Comparator<?> orderBy);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#findAll(Filter, Remote.Comparator)
	 */
	Collection<T> findAll(Filter<?> filter, Remote.Comparator<?> orderBy);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#saveAll(Object[])
	 */
	void saveAll(T... entities);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#saveAll(Collection)
	 */
	void saveAll(Collection<? extends T> colEntities);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#saveAll(Stream)
	 */
	void saveAll(Stream<? extends T> strEntities);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#get(Object, ValueExtractor)
	 */
	<R> R get(ID id, ValueExtractor<? super T, ? extends R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#get(Object, ValueExtractor[])
	 */
	@SuppressWarnings("unchecked")
	Fragment<T> get(ID id, ValueExtractor<? super T, ?>... extractors);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#getAll(ValueExtractor)
	 */
	<R> Map<ID, R> getAll(ValueExtractor<? super T, ? extends R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#getAll(Collection, ValueExtractor)
	 */
	<R> Map<ID, R> getAll(Collection<? extends ID> colIds, ValueExtractor<? super T, ? extends R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#getAll(Filter, ValueExtractor)
	 */
	<R> Map<ID, R> getAll(Filter<?> filter, ValueExtractor<? super T, ? extends R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#getAll(ValueExtractor[])
	 */
	@SuppressWarnings("unchecked")
	Map<ID, Fragment<T>> getAll(ValueExtractor<? super T, ?>... extractors);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#getAll(Collection, ValueExtractor[])
	 */
	@SuppressWarnings("unchecked")
	Map<ID, Fragment<T>> getAll(Collection<? extends ID> colIds, ValueExtractor<? super T, ?>... extractors);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#getAll(Filter, ValueExtractor[])
	 */
	@SuppressWarnings("unchecked")
	Map<ID, Fragment<T>> getAll(Filter<?> filter, ValueExtractor<? super T, ?>... extractors);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#update(Object, ValueUpdater, Object)
	 */
	<U> void update(ID id, ValueUpdater<? super T, ? super U> updater, U value);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#update(Object, ValueUpdater, Object, EntityFactory)
	 */
	<U> void update(ID id, ValueUpdater<? super T, ? super U> updater, U value,
			EntityFactory<? super ID, ? extends T> factory);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#update(Object, Remote.Function)
	 */
	<R> R update(ID id, Remote.Function<? super T, ? extends R> updater);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#update(Object, Remote.Function, EntityFactory)
	 */
	public <R> R update(ID id, Remote.Function<? super T, ? extends R> updater,
			EntityFactory<? super ID, ? extends T> factory);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#update(Object, Remote.BiFunction, Object)
	 */
	<U, R> R update(ID id, Remote.BiFunction<? super T, ? super U, ? extends R> updater,
			U value);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#update(Object, Remote.BiFunction, Object, EntityFactory)
	 */
	<U, R> R update(ID id, Remote.BiFunction<? super T, ? super U, ? extends R> updater, U value,
			EntityFactory<? super ID, ? extends T> factory);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#updateAll(Filter, ValueUpdater, Object)
	 */
	<U> void updateAll(Filter<?> filter, ValueUpdater<? super T, ? super U> updater, U value);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#updateAll(Filter, Remote.Function)
	 */
	<R> Map<ID, R> updateAll(Filter<?> filter, Remote.Function<? super T, ? extends R> updater);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#updateAll(Filter, Remote.BiFunction, Object)
	 */
	<U, R> Map<ID, R> updateAll(Filter<?> filter,
			Remote.BiFunction<? super T, ? super U, ? extends R> updater, U value);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#remove(Object, boolean)
	 */
	T delete(T entity, boolean fReturn); // TODO remap in base class

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeAllById(Collection)
	 */
	boolean deleteAllById(Collection<? extends ID> colIds); // TODO remap in base class

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeAllById(Collection, boolean)
	 */
	Map<ID, T> deleteAllById(Collection<? extends ID> colIds, boolean fReturn); // TODO remap in base class

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeAll(Object[])
	 */
	boolean deleteAll(T... entities); // TODO remap in base class

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeAll(Collection)
	 */
	boolean deleteAll(Collection<? extends T> colEntities); // TODO remap in base class

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeAll(Collection, boolean)
	 */
	Map<ID, T> removeAll(Collection<? extends T> colEntities, boolean fReturn);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeAll(Stream)
	 */
	boolean removeAll(Stream<? extends T> strEntities);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeAll(Stream, boolean)
	 */
	Map<ID, T> removeAll(Stream<? extends T> strEntities, boolean fReturn);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeAll(Filter)
	 */
	boolean removeAll(Filter<?> filter);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeAll(Filter)
	 */
	Map<ID, T> removeAll(Filter<?> filter, boolean fReturn);

	// ---- Stream API support ----------------------------------------------

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#stream()
	 */
	RemoteStream<T> stream();

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#stream(Collection)
	 */
	RemoteStream<T> stream(Collection<? extends ID> colIds);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#stream(Filter)
	 */
	RemoteStream<T> stream(Filter<?> filter);

	// ---- aggregation support ---------------------------------------------

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#count(Filter)
	 */
	long count(Filter<?> filter);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#max(Remote.ToIntFunction)
	 */
	int max(Remote.ToIntFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#max(Filter, Remote.ToIntFunction)
	 */
	int max(Filter<?> filter, Remote.ToIntFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#max(Remote.ToLongFunction)
	 */
	long max(Remote.ToLongFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#max(Filter, Remote.ToLongFunction)
	 */
	long max(Filter<?> filter, Remote.ToLongFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#max(Remote.ToDoubleFunction)
	 */
	double max(Remote.ToDoubleFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#max(Filter, Remote.ToDoubleFunction)
	 */
	double max(Filter<?> filter, Remote.ToDoubleFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#max(Remote.ToBigDecimalFunction)
	 */
	BigDecimal max(Remote.ToBigDecimalFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#max(Filter, Remote.ToBigDecimalFunction)
	 */
	BigDecimal max(Filter<?> filter, Remote.ToBigDecimalFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#max(Remote.ToComparableFunction)
	 */
	<R extends Comparable<? super R>> R max(Remote.ToComparableFunction<? super T, R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#max(Filter, Remote.ToComparableFunction)
	 */
	<R extends Comparable<? super R>> R max(Filter<?> filter, Remote.ToComparableFunction<? super T, R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#maxBy(ValueExtractor)
	 */
	<R extends Comparable<? super R>> Optional<T> maxBy(ValueExtractor<? super T, ? extends R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#maxBy(Filter, ValueExtractor)
	 */
	<R extends Comparable<? super R>> Optional<T> maxBy(Filter<?> filter, ValueExtractor<? super T, ? extends R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#min(Remote.ToIntFunction)
	 */
	int min(Remote.ToIntFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#min(Filter, Remote.ToIntFunction)
	 */
	int min(Filter<?> filter, Remote.ToIntFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#min(Remote.ToLongFunction)
	 */
	long min(Remote.ToLongFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#min(Filter, Remote.ToLongFunction)
	 */
	long min(Filter<?> filter, Remote.ToLongFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#min(Remote.ToDoubleFunction)
	 */
	double min(Remote.ToDoubleFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#min(Filter, Remote.ToDoubleFunction)
	 */
	double min(Filter<?> filter, Remote.ToDoubleFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#min(Remote.ToBigDecimalFunction)
	 */
	BigDecimal min(Remote.ToBigDecimalFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#min(Filter, Remote.ToBigDecimalFunction)
	 */
	BigDecimal min(Filter<?> filter, Remote.ToBigDecimalFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#min(Remote.ToComparableFunction)
	 */
	<R extends Comparable<? super R>> R min(Remote.ToComparableFunction<? super T, R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#min(Filter, Remote.ToComparableFunction)
	 */
	<R extends Comparable<? super R>> R min(Filter<?> filter, Remote.ToComparableFunction<? super T, R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#minBy(ValueExtractor)
	 */
	<R extends Comparable<? super R>> Optional<T> minBy(ValueExtractor<? super T, ? extends R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#minBy(Filter, ValueExtractor)
	 */
	<R extends Comparable<? super R>> Optional<T> minBy(Filter<?> filter, ValueExtractor<? super T, ? extends R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#sum(Remote.ToIntFunction)
	 */
	long sum(Remote.ToIntFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#sum(Filter, Remote.ToIntFunction)
	 */
	long sum(Filter<?> filter, Remote.ToIntFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#sum(Remote.ToLongFunction)
	 */
	long sum(Remote.ToLongFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#sum(Filter, Remote.ToLongFunction)
	 */
	long sum(Filter<?> filter, Remote.ToLongFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#sum(Remote.ToDoubleFunction)
	 */
	double sum(Remote.ToDoubleFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#sum(Filter, Remote.ToDoubleFunction)
	 */
	double sum(Filter<?> filter, Remote.ToDoubleFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#sum(Remote.ToBigDecimalFunction)
	 */
	BigDecimal sum(Remote.ToBigDecimalFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#sum(Filter, Remote.ToBigDecimalFunction)
	 */
	BigDecimal sum(Filter<?> filter, Remote.ToBigDecimalFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#average(Remote.ToIntFunction)
	 */
	double average(Remote.ToIntFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#average(Filter, Remote.ToIntFunction)
	 */
	double average(Filter<?> filter, Remote.ToIntFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#average(Filter, Remote.ToLongFunction)
	 */
	double average(Remote.ToLongFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#average(Filter, Remote.ToLongFunction)
	 */
	double average(Filter<?> filter, Remote.ToLongFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#average(Filter, Remote.ToDoubleFunction)
	 */
	double average(Remote.ToDoubleFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#average(Filter, Remote.ToDoubleFunction)
	 */
	double average(Filter<?> filter, Remote.ToDoubleFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#average(Filter, Remote.ToBigDecimalFunction)
	 */
	BigDecimal average(Remote.ToBigDecimalFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#average(Filter, Remote.ToBigDecimalFunction)
	 */
	BigDecimal average(Filter<?> filter, Remote.ToBigDecimalFunction<? super T> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#distinct(ValueExtractor)
	 */
	<R> Collection<? extends R> distinct(ValueExtractor<? super T, ? extends R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#distinct(Filter, ValueExtractor)
	 */
	<R> Collection<? extends R> distinct(Filter<?> filter,
			ValueExtractor<? super T, ? extends R> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#groupBy(ValueExtractor)
	 */
	<K> Map<K, Set<T>> groupBy(ValueExtractor<? super T, ? extends K> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#groupBy(ValueExtractor, Remote.Comparator)
	 */
	<K> Map<K, SortedSet<T>> groupBy(ValueExtractor<? super T, ? extends K> extractor,
			Remote.Comparator<? super T> orderBy);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#groupBy(Filter, ValueExtractor)
	 */
	<K> Map<K, Set<T>> groupBy(Filter<?> filter, ValueExtractor<? super T, ? extends K> extractor);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#groupBy(Filter, ValueExtractor, Remote.Comparator)
	 */
	<K> Map<K, SortedSet<T>> groupBy(Filter<?> filter, ValueExtractor<? super T, ? extends K> extractor,
			Remote.Comparator<? super T> orderBy);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#groupBy(ValueExtractor, RemoteCollector)
	 */
	<K, A, R> Map<K, R> groupBy(ValueExtractor<? super T, ? extends K> extractor,
			RemoteCollector<? super T, A, R> collector);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#groupBy(Filter, ValueExtractor, RemoteCollector)
	 */
	<K, A, R> Map<K, R> groupBy(Filter<?> filter, ValueExtractor<? super T, ? extends K> extractor,
			RemoteCollector<? super T, A, R> collector);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#groupBy(ValueExtractor, Remote.Supplier, RemoteCollector)
	 */
	<K, A, R, M extends Map<K, R>> M groupBy(ValueExtractor<? super T, ? extends K> extractor,
			Remote.Supplier<M> mapFactory, RemoteCollector<? super T, A, R> collector);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#groupBy(Filter, ValueExtractor, RemoteCollector)
	 */
	<K, A, R, M extends Map<K, R>> M groupBy(Filter<?> filter,
			ValueExtractor<? super T, ? extends K> extractor, Remote.Supplier<M> mapFactory,
			RemoteCollector<? super T, A, R> collector);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#top(ValueExtractor, int)
	 */
	<R extends Comparable<? super R>> List<R> top(ValueExtractor<? super T, ? extends R> extractor, int cResults);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#top(Filter, ValueExtractor, int)
	 */
	<R extends Comparable<? super R>> List<R> top(Filter<?> filter, ValueExtractor<? super T, ? extends R> extractor, int cResults);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#top(ValueExtractor, Remote.Comparator, int)
	 */
	<R> List<R> top(ValueExtractor<? super T, ? extends R> extractor, Remote.Comparator<? super R> comparator, int cResults);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#top(Filter, ValueExtractor, Remote.Comparator, int)
	 */
	<R> List<R> top(Filter<?> filter, ValueExtractor<? super T, ? extends R> extractor, Remote.Comparator<? super R> comparator, int cResults);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#topBy(ValueExtractor, int)
	 */
	<R extends Comparable<? super R>> List<T> topBy(ValueExtractor<? super T, ? extends R> extractor, int cResults);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#topBy(Filter, ValueExtractor, int)
	 */
	<R extends Comparable<? super R>> List<T> topBy(Filter<?> filter, ValueExtractor<? super T, ? extends R> extractor, int cResults);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#topBy(Remote.Comparator, int)
	 */
	List<T> topBy(Remote.Comparator<? super T> comparator, int cResults);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#topBy(Filter, Remote.Comparator, int)
	 */
	List<T> topBy(Filter<?> filter, Remote.Comparator<? super T> comparator, int cResults);

	// ----- listener support -----------------------------------------------

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#addListener(AbstractRepository.Listener)
	 */
	void addListener(AbstractRepository.Listener<? super T> listener);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeListener(AbstractRepository.Listener)
	 */
	void removeListener(AbstractRepository.Listener<? super T> listener);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#addListener(Object, AbstractRepository.Listener)
	 */
	void addListener(ID id, AbstractRepository.Listener<? super T> listener);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeListener(Object, AbstractRepository.Listener)
	 */
	void removeListener(ID id, AbstractRepository.Listener<? super T> listener);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#addListener(Filter, AbstractRepository.Listener)
	 */
	void addListener(Filter<?> filter, AbstractRepository.Listener<? super T> listener);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#removeListener(Filter, AbstractRepository.Listener)
	 */
	void removeListener(Filter<?> filter, AbstractRepository.Listener<? super T> listener);

	/**
	 * (non-Javadoc)
	 *
	 * @see com.oracle.coherence.repository.AbstractRepository#listener()
	 */
	AbstractRepository.Listener.Builder<T> listener();
}
