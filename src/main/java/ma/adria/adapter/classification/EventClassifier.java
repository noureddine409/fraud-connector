package ma.adria.adapter.classification;

import java.util.Map;


/**
 * Interface for classifying events.
 * <p>
 * This interface provides a method to classify events based on their attributes.
 * Implementations of this interface are expected to define specific rules
 * to determine the classification of an event.
 * </p>
 */
public interface EventClassifier {

    /**
     * Classifies the given event based on its attributes.
     * <p>
     * This method takes a map representing an event row, extracts relevant
     * information, and returns the appropriate {@link EventClassification}.
     * </p>
     *
     * @param eventRow a map representing the event, where keys are attribute
     *                 names and values are attribute values
     * @return the classification of the event as an {@link EventClassification}
     */
    EventClassification classify(final Map<String, Object> eventRow);

}
