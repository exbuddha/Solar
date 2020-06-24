package performance;

/**
 * {@code Decision} classifies data types that are in charge of making all forms of decision makings during performance.
 * <p>
 * This is highly abstract conceptual interface used for bringing data types that represent human active preferences under one umbrella.
 * It is declared as {@code Unit} in order for its instances to be represented in performer change graphs, by design, and as {@code Group} in order to isolate the instances from regular performance units and to give them special significance.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Decision
extends
    Group,
    performance.system.Type<Interaction.Conversion<?,?,?,?>>,
    Unit
{}