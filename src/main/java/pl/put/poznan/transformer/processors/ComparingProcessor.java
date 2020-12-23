package pl.put.poznan.transformer.processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.models.DTO.ComparableJSONDTO;
import pl.put.poznan.transformer.models.DTO.FilterableJSONDTO;
import pl.put.poznan.transformer.models.DTO.JSONDTO;
import pl.put.poznan.transformer.models.DTO.StringDTO;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

/**
 * Class use to do drop process
 *
 * @author Piotr Tylczynski
 * @version 1.0
 */

public class ComparingProcessor extends Processor{

    private final Logger logger = LoggerFactory.getLogger(ComparingProcessor.class);

    private final Optional<Processor> processor;

    /**
     * Main ComparingProcessor constructor
     */
    public ComparingProcessor() {
        this.processor = Optional.empty();
    }

    /**
     * ComparingProcessor constructor which is used
     * when we want to activate more than just one processor
     *
     * @param processor another processor to start
     */

    public ComparingProcessor(Processor processor) {
        this.processor = Optional.of(processor);
    }

    /**
     * Recursive processors running and comparing
     *
     * @param from prepared data
     * @return processed data
     */

    @Override
    public JSONDTO process(JSONDTO from) {
        this.logger.debug("Comparing request");
        if(this.processor.isPresent())
            from = this.processor.get().process(from);
        // if JSONDTO is not subclass of FilterableDTO then omit
            Set<String> diff = new HashSet<>();
            if(from.getClass().isAssignableFrom(ComparableJSONDTO.class)){
                for (Iterator<String> it = ((JsonNode) ((ComparableJSONDTO) from).getJson1().getInput()).fieldNames(); it.hasNext(); ) {
                    String field = it.next();
                    if(!((ComparableJSONDTO) from).getJson1().getInput().get(field).equals(
                            ((ComparableJSONDTO) from).getJson2().getInput().get(field))
                    ){
                        diff.add("(" + field + ")");
                    }
                }
                Set<String> keys1 = Sets.newHashSet(((JsonNode) ((ComparableJSONDTO) from).getJson1().getInput()).fieldNames());
                Set<String> keys2 = Sets.newHashSet(((JsonNode) ((ComparableJSONDTO) from).getJson2().getInput()).fieldNames());
                for(String s : keys1){
                    if(!keys2.contains(s)){
                        diff.add("(" + s + ")");
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Differences:");
            for(String s : diff) sb.append(" ").append(s);
        from.setOutput(
                sb.toString()
        );
        return from;
    }
}
