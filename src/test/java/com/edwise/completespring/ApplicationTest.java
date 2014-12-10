package com.edwise.completespring;

import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.SequenceId;
import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    private static final int ONE_TIME = 1;
    private static final int FOUR_TIMES = 4;

    @Mock
    BookRepository bookRepository;

    @Mock
    SequenceIdRepository sequenceRepository;

    @InjectMocks
    private Application application  = new Application();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(this.application, "resetAndLoadOnStartup", true);
    }

    @Test
    public void testRun() throws Exception {
        application.run("");

        verify(sequenceRepository, times(ONE_TIME)).save(any(SequenceId.class));
        verify(bookRepository, times(ONE_TIME)).deleteAll();
        verify(bookRepository, times(FOUR_TIMES)).save(any(Book.class)); // Número de libros insertados de ejemplo al arrancar
    }
}
