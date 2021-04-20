package com.oracle.coherence.spring.data.model.repositories;

import com.oracle.coherence.spring.data.model.Book;
import com.oracle.coherence.spring.data.repository.CoherenceRepository;
import com.tangosol.util.UUID;

@com.oracle.coherence.spring.data.config.CoherenceRepository("book")
public interface CoherenceBookRepository extends CoherenceRepository<Book, UUID> {


}
