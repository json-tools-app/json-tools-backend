package types;

import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.controllers.MinifyingController;
import pl.put.poznan.transformer.processors.*;

import static org.junit.jupiter.api.Assertions.*;

public class InheritanceTest {
    @Test
    void is_beautyfy_porcessor_prcessor(){
        assertTrue(Processor.class.isAssignableFrom(BeautyfyProcessor.class));
    }

    @Test
    void is_comparing_porcessor_prcessor(){
        assertTrue(Processor.class.isAssignableFrom(ComparingProcessor.class));
    }

    @Test
    void is_dropping_porcessor_prcessor(){
        assertTrue(Processor.class.isAssignableFrom(DroppingProcessor.class));
    }

    @Test
    void is_filter_porcessor_prcessor(){
        assertTrue(Processor.class.isAssignableFrom(FilterProcessor.class));
    }

    @Test
    void is_minifying_porcessor_prcessor(){
        assertTrue(Processor.class.isAssignableFrom(MinifyingProcessor.class));
    }
}
