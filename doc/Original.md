Performance
===========

The **performance** package contains classes and interfaces that represent the knowledge of performance and all the decision makings that go into it. This is the main package of the project. It currently holds definitions of the smaller entities and a general and simplified skeleton of a model for making performance decisions as a performer does in real life.

Conductor
---------

The Conductor class contains the main logic and entry point of the project.

Instrument and Instrument.Part
------------------------------

The Instrument class represents a set of parts acting as the superclass for all musical instruments, instrument parts, and the human body. Each instrument  (derivative of Instrument class) also holds its own class of accessories that can be used with the instrument. This is necessary for those musical instruments that are played with an external part such as the pick or the drum sticks. How these accessories are connected to the body parts or are used against the instrument parts is a knowledge that belongs to ASM and performer logic.

Part is an entity that represents a physical piece of an instrument which is subject to or of similar occurrences in a certain logical context.

These two classes will ultimately define a musical instrument and human body parts, in their respective hierarchies, with enough details such that all parts can be geometrically defined and graphically constructed.

Performer
---------

The Performer class is the superclass for all concrete performer classes that are in charge of providing performance-related logic for carrying out the musical tasks on an instrument. These classes, housed in the performers package, define the abstract methods in Performer superclass, or an abstract subclass of it, that are meant to break down the ASM logic into general units meaningful across all or similar instrument types.

State
-----

State represents the state of a part. It can be as simple as a singleton or more complex as an arbitrary object depending on the part and its role in performance.

Snapshot
--------

Snapshot is, as the name suggests, a snapshot in time of the states of all parts involved in the performance. The instances of this class are used by the ASM logic to trace the instrument and body part states as the music progresses in order to make the decision making process dynamic.

Interpreter
-----------

The Interpreter class represents an entity in the project that translates the data in the score into a form of data recognizable by both ASM and Performer classes. An interpreter's main task is to generate instances of music based on the elements in the score and to further provide information about the nature of these instances such as phrase discovery and other functionality that help performers in real life make smart decisions.

Instance
--------

An Instance is a representation of moment in music as a duration in reference to the beginning of the score. Each instance connotes to a certain action or a group of them that need to be played out. Instances are not instrument-specific data. They generally define the intentions reflected in the score that will later on be translated to instrument-specific instructions.

Instruction
-----------

The Instruction class represents a set of interactions that are meant to carry out the performance demands of an instance in the score. For example, a rest in the score usually means that all the previous on-going interactions are to be stopped, paused, or reversed. All the physical tasks performed by the performer to make this happen are considered to be a part of the instruction for that instance.

Change, Interaction, and Technique
----------------------------------

The Change class represents the classification of interactions in performance. It is the nucleus for all interactions and can be permuted to generate individual instances of interactions that define the atomic pieces for performing a musical instrument. Every instance of change contains a list of masculine and feminine parts. A masculine part is the body part, accessory, or an instrument part that is used to initiate an action on the musical instrument in order to create or change the sound or to make other interactions on that instrument possible. A feminine part is the part (or body) of the musical instrument that is acted upon by the masculine part and reacts to create or change sound. Furthermore, each change can be classified in three contexts: Effect Type, Action Type, and Reaction Type. There are multiple categories in each classification and a subclass of the Change class can be of one or more categories in each of the three contexts. These categories are described per instrument in more details below.

Interactions are individual instances of change. All interaction instances of a change share the same Change object but define their own specific masculine and feminine parts, or any other data specific to the interaction. Each instrument maintains a list of changes specifically for that instrument. Interactions that originate from these changes can be instantaneous, durational, or repetitive as the Action and Reaction type classifications also indicate. For example, for the guitar, some of the main change classes are defined as Pluck, Press, HammerOn, PullOff, Tremolo, Touch, Release, Slide, Trill, Vibrato, Bend, and BendRelease.

Technique is a single interaction or a combination of interactions that can be reused to perform a part of music. Technique refers to the well-known actions performed on a musical instrument. Complex techniques can be created by grouping many interactions either instantly, separated over a time duration, or repeating over a time duration. For example, for the guitar, two interactions of different hands can be combined to create simple techniques -- a press interaction of the left hand and a pluck interaction of the right hand can be combined at the same time to form the most basic technique for playing a note on the guitar.

Moving a bit further into the logic for finding the right interactions to play, the change instances of each instrument are tied together in a graph that facilitates in part of the just-in-time decision making associated with that instrument. For example, two change instances can be related together based on whether one can lead to the other or even contain various meta-data showing under what circumstances this can happen. The performance rules and preferences can then be added to the graph or be reused to form a set, for now, called a rule set. Rule sets are a way of grouping rules that apply to the performance at some time or depth in the ASM logic.

**NOTES:**

- Some interactions can be categorized as naturally common among all instruments or a group of instruments. One such interaction is Position that is a requirement for all instruments that allow the body part to be in different positions in order to perform two or more interactions in time. This applies to all instruments that are large enough for the body part's reach to cover the entire scale in a phrase. Therefore, for the purpose of making the performance logic as general as possible, it makes sense to define a super-type for such interactions and leave it to the performance and the instrument logic to determine when such interactions are available or might be required. This means that these special interactions, their outcomes, usages, or effects can potentially maintain a single unified meaning in the model across all such instruments whereas most instrument-specific interactions don't require this generality and can merely be treated as possibilities for generating certain effects on the instrument.

### States

Each instrument and body part can have one or many states at any given time. States are defined in the context of performance such that, by themselves, they are well-defined and contain enough information that allows the underlying logic to determine the type of action that the body part must perform and the type of reaction that the instrument would respond with, in sufficient details. For all parts, the IDLE state indicates that the part is engaged in no particular action and is free. States are a crucial part of the model and are widely used by the action selection algorithm in order to populate and lighten the execution space at each iteration of the process. States and their meaning are maintained by the intelligent units in the model. In other words, states are pre- and well-defined entities in the model so they are available in their most normalized form.

### Effect Types

Effect types define a categorization for the types of effects that changes (and their interactions) can produce in the aural field. The effect types are defined as:
- **Unitary:** Uniquely and authoritatively affects the aural field in a way that no other unitary change (on the same instrument part) can coexist with.
    - **Productive:** Produces new sound and excites the aural field.
- **Secondary:** Affects the aural field only when the instrument part produces, or is producing, a unitary change and not otherwise.
- **Tertiary:** Does not affect the aural field in any way. (Classifies silent changes with actions intended as a preparation for another change)
- **Reductive:** Reduces active sound and quiets the aural field.
- **Melodic:** Intended to produce melody by altering pitch of sound.
    - **Pitched:** Alters pitch of sound by itself alone.
        * **Copitched:** Alters to a pitch that depends on the state of the instrument part or is in collaboration with another change. This classifies the more complex changes in an instrument that involve interacting with more than one part in order to produce a certain effect, or sonically depend on the state of another part or parts.
        * **Glide:** Alters pitch in a sliding manner sounding all available pitches up/down to the final pitch.
        * **Undetermined:** Alters pitch in an undetermined manner or is not intended to have an exact pitch.
    - **Unpitched:** Does not alter pitch of sound by itself alone and the pitch is totally determined by another change or an instrument part state.
        * **Subtle:** Alters pitch only if the instrument part or its larger containing body is in a productive state that doesn't end by the effect of this change.
- **Repetitive:** Repeats the same effect periodically over time.
    - **Alternative:** Alternates pitch or quality of sound.
    - **Vibrational:** Vibrates pitch or quality of sound.
- **Tonal:** Alters the active tone quality of sound.

**NOTES:**

- A glide effect type is always characterized as a continuous action, however, since all continuous actions can be instantaneous when performed quickly enough, the gliding effect (glissando) can be neglected and the change can be treated as an instantaneous pitched change.
- There is an inherent governing hierarchy among effect types regarding an instrument when two or more changes are affecting the instrument which is specific to that instrument or instrument type. This knowledge, not reflected in this categorization, is in charge of determining which change or changes become effective in the aural field in cases where some generated possibilities are physically unrealistic. It is a part of the performance logic that deals with selecting the change and interaction objects during performance of a score. For example, two unitary changes can overlap each other such that one becomes effective and the other ineffective. If a guitar player mutes the strings with one hand and performs a hammer-on with the other hand at a fret behind the reach of the muting hand, there will be no sound produced or a muted unpitched sound is heard. In this case, the mute change makes the hammer-on change ineffective and becomes the overshadowing unitary change. It is also possible that some of the combinations, such as the one described here, would lead to a brand new technique. The work of determining such cases must be done at every iteration of the action selection algorithm in order to correct the instrument part state for the next iteration.

### Action Types

Action types define a categorization for the types of actions that body parts, which produce changes, can take on instrument parts. The action types are defined as:
- **Instantaneous:** Burst engagement negligible in time.
- **Continuous:** Sustained engagement noticeable in time required for the entire or a fraction of the duration of the interaction.
    - **Repetitive:** Repeats the same action periodically over time.
- **Unilateral:** Can be performed only by one of the body orientations.
- **Variational:** Varies in physical performance characteristics.
    - **Bilateral:** Can be performed uniquely differently by either of the body orientations with one usually, but not necessarily, preferred over the other.
    - **Directional:** Can be performed in multiple body movement directions in relation to the instrument part.
    - **Gradual:** Varies in physical intensity producing gradual degrees in the effect. (A normal or medium degree is naturally implied)
    - **Accelerational:** Can be performed with constant or variable acceleration or repetition rate.
- **Non-accelerational:** Can only be performed with constant acceleration or repetition rate.
- **Positional:** Requires the body part to be in a certain position (or range) relative to the instrument part.

**NOTES:**

- A continuous action type that takes negligible duration can be instantaneous. These cases are handled in the performance logic rather than the change definition because they are rare and, since in reality every change takes up a duration in time, the instantaneous category is intended to classify those changes for which time duration cannot or is not required to be calculated. Therefore, for all such continuous interactions there is an instantaneous counter-action that reverses or ends the action allowing it to be performed fast enough to be considered instantaneous. For example, the right hand mute interaction for the guitar has a release counter-interaction that does just what was described. This counter relationship between interactions is determined in the change graph of each instrument by the use of a specific edge type.
- When an action type is continuous it means that the body part is occupied for the duration of the interaction and cannot be engaged in any other interaction without stopping its current action.
- Some instantaneous actions have fall-back changes that they immediately end up in after they are performed. For example, a hammer-on interaction immediately falls back to press interaction, or a trill interaction can immediately fall back to press or release. This information can either be represented by an edge in the change graph or by the state adjustment of the ASM logic. An important concept to grasp correctly here is the difference between an instantaneous and a continuous action and differentiating that from the IDLE body state.
- When an action type is neither unilateral nor bilateral it can be performed similarly by both orientations.

### Reaction Types

Reaction types define a categorization for the types of reactions that instrument parts, which produce sound, can show in response to the actions of the body parts. The reaction types are defined as:
- **Instantaneous:** Burst reaction negligible in time.
- **Continuous:** Lasting reaction noticeable in time until another reaction occurs or the reaction naturally decays.
    - **Sustained:** Lasting reaction that diminishes as soon as the action stops or changes.
        * **Repetitive:** Repeats the same reaction periodically over time.
    - **Lasting:** Lasting reaction after the action stops independently of the action type.
    - **Decaying:** Decreasing in loudness (aural energy output) after the action is performed. (Applies to productive effect types only)

**NOTES:**

- Just as is for action types, a continuous reaction type that takes negligible duration can be instantaneous.
- When a reaction type is continuous it means that the instrument part is occupied for the duration of the interaction and cannot be engaged in any other interaction without interrupting or changing its current state. This interruption can be an accepted one depending on the instrument. For example, in a violin performance, at the end of the bow it is possible and acceptable to change the bow direction to extend the note even though a short discontinuity in the effect is inevitable.
- When a reaction type is sustained it means that its effective duration is tied to the duration of the continuous action that produces the reaction and that the effect will continue as long as the action continues. Whether the effect lasts or not after that point is indicated by another reaction type: Lasting.

### Guitar Changes/Interactions

<table>
<tr><td><b>Interaction</b></td><td><b>Effect Type</b></td><td><b>Action Type</b></td><td><b>Reaction Type</b></td></tr>
<tr><td colspan="4"><b>Both Hands</b></td></tr>
<tr><td>Pluck</td><td>Productive, Unpitched</td><td>Instantaneous, Bilateral, Directional, Gradual</td><td>Lasting, Decaying</td></tr>
<tr><td>Press</td><td>Secondary, Pitched</td><td>Continuous, Bilateral, Positional</td><td>Sustained</td></tr>
<tr><td>Release (Finger)</td><td>Secondary, Reductive, Subtle, Tertiary</td><td>Instantaneous, Bilateral</td><td>Lasting</td></tr>
<tr><td>Hammer-on</td><td>Productive, Pitched</td><td>Continuous, Bilateral, Gradual, Positional</td><td>Sustained, Decaying</td></tr>
<tr><td>Pull-off</td><td>Productive, Pitched</td><td>Instantaneous, Bilateral, Gradual, Positional</td><td>Lasting, Decaying</td></tr>
<tr><td>Position</td><td>Tertiary</td><td>Continuous, Bilateral</td><td>Lasting</td></tr>
<tr><td>Vibrato</td><td>Secondary, Vibrational</td><td>Repetitive, Bilateral, Gradual, Accelerational</td><td>Repetitive</td></tr>
<tr><td>Trill</td><td>Productive, Pitched, Alternative</td><td>Repetitive, Bilateral, Gradual, Accelerational, Positional</td><td>Repetitive</td></tr>
<tr><td>Slide (Finger)</td><td>Secondary, Glide</td><td>Continuous, Bilateral, Accelerational, Positional</td><td>Sustained</td></tr>
<tr><td>Mute</td><td>Unitary, Reductive</td><td>Continuous, Bilateral</td><td>Lasting</td></tr>
<tr><td>Whammy Pull</td><td>Secondary, Glide</td><td>Bilateral, Accelerational, Positional</td><td>Sustained</td></tr>
<tr><td>Whammy Push</td><td>Secondary, Glide</td><td>Bilateral, Accelerational, Positional</td><td>Sustained</td></tr>
<tr><td>Whammy Release</td><td>Secondary, Glide</td><td>Bilateral, Accelerational, Positional</td><td>Lasting</td></tr>
<tr><td>Whammy Vibrato</td><td>Secondary, Vibrational</td><td>Repetitive, Bilateral, Gradual, Accelerational, Positional</td><td>Repetitive</td></tr>
<tr><td colspan="4"><b>Right Hand</b></td></tr>
<tr><td>Pick</td><td>Productive, Unpitched</td><td>Instantaneous, Unilateral, Directional, Gradual</td><td>Lasting, Decaying</td></tr>
<tr><td>Palm Mute</td><td>Secondary, Tonal</td><td>Continuous, Unilateral, Positional</td><td>Sustained</td></tr>
<tr><td>Palm Release</td><td>Secondary</td><td>Instantaneous, Unilateral, Positional</td><td>Lasting</td></tr>
<tr><td>Tremolo (Finger)</td><td>Productive, Repetitive</td><td>Repetitive, Unilateral, Gradual, Accelerational</td><td>Repetitive</td></tr>
<tr><td>Tremolo (Pick)</td><td>Productive, Repetitive</td><td>Repetitive, Unilateral, Gradual, Accelerational</td><td>Repetitive</td></tr>
<tr><td>Pinch Harmonic</td><td>Productive, Copitched, Tonal</td><td>Instantaneous, Unilateral, Directional, Gradual, Positional</td><td>Lasting, Decaying</td></tr>
<tr><td>Tap Harmonic</td><td>Productive, Copitched, Tonal</td><td>Instantaneous, Unilateral, Positional</td><td>Lasting, Decaying</td></tr>
<tr><td>Pick Slide</td><td>Productive, Copitched, Glide, Undetermined, Tonal</td><td>Unilateral, Accelerational, Positional</td><td>Sustained</td></tr>
<tr><td colspan="4"><b>Left Hand</b></td></tr>
<tr><td>Bend</td><td>Secondary, Glide</td><td>Continuous, Unilateral, Directional, Accelerational, Positional</td><td>Sustained</td></tr>
<tr><td>Bend Release</td><td>Secondary, Glide</td><td>Continuous, Unilateral, Directional, Accelerational, Positional</td><td>Sustained</td></tr>
<tr><td>Bar</td><td>Secondary, Pitched</td><td>Continuous, Unilateral, Positional</td><td>Sustained</td></tr>
<tr><td>Slide (Bar)</td><td>Secondary, Glide</td><td>Continuous, Unilateral, Accelerational, Positional</td><td>Sustained</td></tr>
<tr><td>Vibrato (Bar)</td><td>Secondary, Vibrational</td><td>Repetitive, Unilateral, Gradual, Accelerational, Positional</td><td>Repetitive</td></tr>
<tr><td>Release (Bar)</td><td>Secondary, Reductive, Subtle, Tertiary</td><td>Instantaneous, Unilateral, Positional</td><td>Lasting</td></tr>
<tr><td>Slide Touch</td><td>Secondary, Unpitched</td><td>Instantaneous, Unilateral, Positional</td><td>Sustained</td></tr>
<tr><td>Slide Tap</td><td>Productive, Pitched</td><td>Instantaneous, Unilateral, Positional</td><td>Sustained</td></tr>
<tr><td>Slide (Slide)</td><td>Secondary, Glide</td><td>Continuous, Unilateral, Accelerational, Positional</td><td>Sustained</td></tr>
<tr><td>Vibrato (Slide)</td><td>Secondary, Vibrational</td><td>Repetitive, Unilateral, Gradual, Accelerational, Positional</td><td>Repetitive</td></tr>
<tr><td>Release (Slide)</td><td>Secondary, Reductive, Subtle, Tertiary</td><td>Instantaneous, Unilateral, Positional</td><td>Lasting</td></tr>
<tr><td>Harmonic (Finger)</td><td>Secondary, Unpitched, Tonal</td><td>Continuous, Unilateral, Positional</td><td>Sustained</td></tr>
<tr><td>Harmonic (Bar)</td><td>Secondary, Unpitched, Tonal</td><td>Continuous, Unilateral, Positional</td><td>Sustained</td></tr>
</table>

**NOTES:**

- Some guitar techniques are missing from the list above because they are a result of an interaction defined under a different name or they are a combination of many interactions from the list. These techniques, such as tap or tap trill, are declared as techniques in the model and are picked up by the logic outside of the iterative approach or per discovery.
- For the left hand harmonic interaction, the natural or artificial types depend on the state of the string. If the string is not pressed by a left hand finger, it becomes a natural harmonic; otherwise, it will be an artificial harmonic. The calculations for the note and frequency in each of these cases will have to be appropriately adjusted based on the harmonic types and the state of the guitar strings and frets.
- The number of changes for the guitar instrument is quite large depending on the level of granularity or purpose, and interest of performers. This does not undermine the validity of the data in the table above. It only means that some changes or interactions, that might indicate a slight overlapping in definitions, need to be sub-typed.

### Piano Changes/Interactions

<table>
<tr><td><b>Interaction</b></td><td><b>Effect Type</b></td><td><b>Action Type</b></td><td><b>Reaction Type</b></td></tr>
<tr><td colspan="4"><b>Hands</b></td></tr>
<tr><td>Press</td><td>Productive, Pitched</td><td>Continuous, Gradual, Positional</td><td>Sustained, Decaying</td></tr>
<tr><td>Release</td><td>Unitary, Reductive, Pitched</td><td>Instantaneous, Positional</td><td>Lasting</td></tr>
<tr><td>Position</td><td>Tertiary</td><td>Continuous</td><td>Lasting</td></tr>
<tr><td>Trill</td><td>Productive, Pitched, Alternative</td><td>Repetitive, Gradual, Accelerational, Positional</td><td>Repetitive</td></tr>
<tr><td>Slide</td><td>Productive, Glide</td><td>Continuous, Gradual, Accelerational, Positional</td><td>Sustained</td></tr>
<tr><td>Pluck</td><td>Productive, Pitched</td><td>Instantaneous, Gradual, Positional</td><td>Lasting, Decaying</td></tr>
<tr><td colspan="4"><b>Feet</b></td></tr>
<tr><td>Pedal Press</td><td>Secondary, Tonal</td><td>Continuous, Positional</td><td>Sustained</td></tr>
<tr><td>Pedal Lock</td><td>Tertiary</td><td>Instantaneous, Positional</td><td>Lasting</td></tr>
<tr><td>Pedal Unlock</td><td>Tertiary</td><td>Instantaneous, Positional</td><td>Lasting</td></tr>
<tr><td>Pedal Release</td><td>Secondary</td><td>Instantaneous, Positional</td><td>Lasting</td></tr>
</table>

Performance, Phrase, and Practice
---------------------------------

These three classes all represent a form of combination of techniques. Phrase is the smallest combination that forms a humanly recognizable chunk of music that can be best characterized as a word phrase or a clause, if the containing musical section is to be imagined as a paragraph in a writing. With this analogy, the individual techniques will be the words and the interactions will be the letters in those words. It is possible to define longer phrases that are a sequence of smaller phrases.

Practice is a sequence of phrases or techniques that can be formulated or parameterized for the sake of repetition. See [Guitar Fundamental Practices](https://github.com/exbuddha/Solar/tree/master/doc/GuitarFundamentalPractices.md) for a better understanding of the concepts underlying and the role of interactions, techniques, and phrases in practice. The methodology explained in this document can be extended to any instrument and provides a good base knowledge for designing the solution for this project.

Performance represents a sequence of interactions between humans and instruments for the purpose of realizing music or transferring it from notation form to physical form. The ultimate goal of this project is to parse the music score and create a finite set of performances for that score. At the core of any performance there is a score that holds the data describing two types of information: one is the effects in the field of performance (aural field) desired by the creator (conceptual or physical), and the other is the specific instructions for the performer, which is indirectly related to the former piece of information, to generate those sought-after effects more specifically or closely to how the creator intended them to be. Generally, the more relevant information a score contains the easier the process of transformation to performance instructions.

Music performance by nature has an infinite problem space. The same score can be performed infinitely differently and sound negligibly the same. A music score can only hold so many performance instructions and the decision makings are partly governed by external factors such as the physical characteristics of the performer's body (anatomical or momentary), the performer's mood (emotional or mental preferences), or physics and the mechanics of the musical instrument. Therefore, action selection in a performance can be immensely cumbersome, heavily data-driven, and in rare cases probability-driven or even random. Generally speaking, the scope of the solution is not outside of the computational boundaries since it is a problem that has already been solved by the human brain. There are teachers and self-practitioners of music who have taught and achieved this knowledge only by practicing. The challenge comes in when one starts to consider performance from the level of human brain activity and tries to break down the process into smaller decision makings. Just like a chess game where there are many possible outcomes at any given time depending on the decisions made earlier in the game, music performance has many possible outcomes with the difference that the set of the parameters that affect the outcomes are far larger than a chess game. Also challenging is the representation of these parameters. Consider the problem of representing all known possible hand gestures and forms on a specific instrument. That by itself is not an easy problem to model. Now, add to that the problem of finding those gestures that are not known or are specific to a certain hand type of an individual. At this point one can ask why should the solution consider those possibilities and why shouldn't it just limit itself to the known body forms. There are two reasons for that. One is that many music pieces are known to challenge the standard body forms developed in performance and require the performer to think outside those boundaries in order to play a certain passage. Another reason is that a goal of this project is to work in total freedom with the human anatomical limitations as much as possible in order to push the envelop in performance and to allow those specifically gifted musicians who can perform an instrument outside the known norms to be represented in the solution.

As it might have already become clear, preferences are a key factor for decision making in this problem and these preferences are not just mechanical or anatomical. For instance, phrasing in a music piece can heavily influence this decision making throughout a performance. How a melody is phrased can affect how the performer decides to play it on an instrument. For example, if a melody has two phrases, it is quite often desirable to start each phrase with a slight emphasis in order to bring out subtle articulations and emphasize the phrases more clearly. This, even though not a hard rule, is a preference widely accepted and respected among performers. This is only one example of many other performance preferences that are recognized in any class of instrument. Another example that applies to instruments that are played by fingers and require full or partial finger movement independence, such as the guitar or the piano, is that the phrasing combined with the instrument mechanics can limit the fingering choices in performance of a phrase to certain fingers only. For example, in some cases where the phrase involves a sequence of notes spanning a scale large enough with note durations fast enough for fingers, it becomes important for the hand to remain stationary or move as little as possible. This, in turn, forces the starting finger to be a finger that allows for other fingers to rapidly and swiftly cover the entire span of the phrase and choosing that first finger depends on the direction of the note sequence in the phrase on the instrument. If it is an upward direction from a low pitch to a high pitch, the best starting finger is most likely the right thumb for the piano or the index finger for the guitar.

It is evident how some very small detail in music performance can affect the decision making in this problem and also how big the set of unwritten rules of performance is, which are globally called preferences in order to bring them under one umbrella.

**NOTES:**

- An immediate need for designing the ASM logic is to identify all preference categories that govern or can determine the performance rules for an instrument as the first step for attempting to represent them in code form. This calls for expert instrumentalists and performers who can explain the process of their own decision making and have encountered a very wide range of music scores.

ASM (Action Selection Model/Mechanism)
--------------------------------------

The nature of ASM for solving the problem of music performance is iterative and recursive. This is because music performance itself is chronological in time meaning that the interactions between the performer's body parts and the instrument parts are tied to time which is ultimately governed by the instances in the score. This fact makes the decision makings of a performer in real life also chronological in time. A performer reading a score and playing it on the instrument needs only to know the current demand of the score plus a finite number of upcoming demands to be able to effectively perform a piece. A performer doesn't need to know the demands of the entire piece ahead of time. Keeping an eye on the current measure and the next few measures usually suffices. The challenge in writing a computer code that does exactly the decision makings a performer does rises from the problem of representing musical knowledge and the physical understanding of the performer, about his or her own body and the instrument, in logical form in a way that the code can iteratively apply to the score in an efficient way. This is where preferences come to help. A description for the requirements of the solution and possible ways to resolve them follows in the next few paragraphs as the first step of formulating a flow chart for action selection.

At this point, having only a big picture of the solution in mind, it seems unlikely that the solution can be achieved in a single pass. This is because figuring out all the performance criteria that can be applied to a music piece in only one pass over the score seems very complex if not impossible. Even if this task is possible in a single pass the logic will have to process parts of the score multiple times in order to identify certain musical events for making intelligent decisions about the next course of action, and for making correct use of preferences, specially the ones related to finding the most appropriate techniques to use or where the phrases start and end. The latter is specially a challenging problem but very useful at the same time because it provides information about the score that can help determine the more humanly calculated solutions since most, if not all, performers think in terms of melodies and phrases during any performance.

Some requirements for the solution are listed below:
 1. First and foremost, the solution needs to work correctly. This implies a lot of things but most importantly it implies that the approach taken must be close to decision makings of a performer in real life or at least generate similar end results. The way this problem is modeled plays an essential role in arriving at the optimal solution. This requirement enforces the fact that the logic must be multi-threaded.
 2. The solution has to be scalable. This means that for any music score a performance has to be formulated in reasonable time, or if the time duration ends up being unreasonably long, there has to be a way to save the intermediate steps of the process so that parts of the solution can be restored in memory quickly afterwards.
 3. The solution has to be general enough so that it can be applied to many or all instruments for a given score.
 4. The solution has to be backward compatible, meaning that there has to be a way to add musical knowledge in the model without losing the benefit of restoring and continuously reusing old results.
 5. Knowing that the size of the problem space can grow large very rapidly, the solution must intelligently constrain that space very early on in the life of the algorithm. This ensures that execution paths that are less likely to lead to a solution will be pruned off earlier than later and will not take up extra processing time for exploration any further than the point they prove to be disposable. To satisfy this general constraint, a series of reduction steps are defined in order to shrink the size of the problem while the solution is being expanded.

Below is a proposed high-level description of an appropriate algorithm for a solution:

Knowing that there is a need governed by the score that has to be satisfied by a performance execution (or interaction) and knowing that the performer's body and the musical instrument and its parts all have a state they can be in at every instance of time, there are two major parts in an iteration of the ASM logic. The first consists of finding what the demands of the score are at the current instance of time and the second consists of finding all possible interactions that satisfy those demands. Naming the first part 'imagination' and the second part 'realization', a realization follows an imagination within each iteration and the projected outcome of each realization becomes a part of the preconditions for imagination in the next iteration. These two processes performed in a loop create threads of possible interactions at every instance of time according to the score which constructs a tree-like structure that is called the execution graph. The reason this is called a graph rather than a tree is that some instances in this tree-like structure can end up being exactly the same interactions (duplicates) and that eliminates the requirement for identifying those interaction-instances individually, converting the tree-like structure into a graph.

ASM defines the functionality to make both the imagination and the realization steps possible. This function abstracts the general logic for all instruments. Different components within ASM define algorithms, specific to the instrument and the mentality of the performer of that instrument, for populating the execution graph. How this is done is a property of ASM communicating with the performer and the instrument, by contract, to make decisions based on specific requirements and restrictions.

Imagination is a more universal process among all instruments; however, in cases where a score is instrument-specific, this process also becomes instrument-specific. Realization is inherently an instrument-specific process. At every iteration, once the current demands of the score are recognized, the parameters that form the preconditions for realizing those demands are the state of the body parts and the instrument parts. These two parameters are used in the ASM logic, along with the output of the imagination process, to determine the set of possible interactions. Each instance in the set, in turn, puts the instrument and the body parts in a different state just as the performer's body and the instrument would be in real life after the performer chooses to go with one of the many possibilities at his or her disposal. The next logical step is to adjust the states. This process is required because many interactions (specially the instantaneous ones) leave the body and the instrument in a state different from the one they indicate in their change instance which led to choosing an interaction in the first place. The next logical step, following state adjustment, is to filter out the interactions and constrain the problem space for upcoming iterations. Optionally, more filtering can be done during the following passes in the logic based on new criteria emerging as the score unfolds and more specific information about it are discovered or become accessible.

As mentioned earlier, the nature of the algorithm is heavily recursive. At each level of recursion, a specific set of preferences are applied to the execution space for filtering out the unwanted branches. For example, looking at the score as a whole, there are most likely not very many preferences that can be determined except for very general facts such as the instrument of performance or its tuning. As the algorithm progresses deeper, more and more preference categories are considered to be eligible. At the phrase level, when a phrase is identified by the algorithm, a whole new set of preferences come forward such as what techniques are to be used for performing the phrase -- shaping style. Those preferences are not important once a decision is made and the algorithm enters a deeper level of its recursion. At the lowest level, where individual notes or events are being worked on, the set of preferences that becomes important determine what interactions or parts are to be used to form the techniques. Respectively, these low-level preferences are no longer valuable at the higher levels where phrases are being identified. Generally speaking, the problem of identifying what set of preferences to use at each step of the algorithm is something that can be solved by employing a rule-based approach.

**NOTES:**

- A graph that connects changes to each other based on various types of truth, like the fact that one can be followed by the other, makes the action selection logic efficient. Considering the edges of this graph, there can be many different types of associations indicating different options such as possible next changes, possible effects of next changes, natural fall-back changes (for example, a trill change can naturally fall back into its hammer-on or press change), or conditionals that allow traversal only if the previous traversal was on a specific edge or groups of edges. The edges must work in multiple complex manners; connecting vertices based on iterative preferences which themselves are based on a wide range of parameters like the state of the parts involved, overall state or restrictions of the instrument and the body, the upcoming demands of or the previous decisions made based on the score, etc. This structure can become a crucial part of the design in a portion of the preference filtering that is performed iteratively.
- The generic logic provides other filtering options that have a scale larger than a single iteration. They can be multi-iterative or on-demand such that when a certain sequence of changes occurs, these filters can provide additional preference scoring application. (sorting)
- As mentioned above, the body and instrument states need to be adjusted at the end of each iteration to count for the fall-back options described earlier. This functionality can be achieved in two distinct ways: either by introducing a step in the iteration for state adjustment, or by defining fall-back changes for changes that naturally fall into another change type or produce a new different reaction type once performed.

Preferences
-----------

The Preference interface aims to outline general and instrument-specific logical units and to encapsulate in an appropriate meta-data a structure for applying filters to a set of possible interactions, for performing a piece of music, in order to restrict the action selection space to a minimum number of interactions. The notion of preference itself and the approach to its design must be broad and flexible if one considers it in details. As mentioned earlier, the interaction space in a performance is inherently large with an exponential growth rate. Preferences aim to mark a reasonably small number of traversal paths in the enormous graph, representing the problem space in performance, as acceptable paths that are worth exploring further and being accepted as possible ways of performing a musical piece.
