package uzuzjmd.competence.logging

/**
 * @author dehne
 */
class ClassNameAdder(ref: AnyRef) { def className = ref.getClass.getName }
