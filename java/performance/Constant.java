package performance;

/**
 * {@code Constant} holds all commonly accepted names and descriptions in music performance.
 */
public final
class Constant
{
    /**
     * {@code ElectricGuitarPlayer} holds interaction and technique names and descriptions common to all or most electric guitar players.
     */
    public
    interface ElectricGuitarPlayer
    extends GuitarPlayer
    {
        final String WhammyPullName = "Whammy Pull";
        final String WhammyPullDesc = "Pull up the whammy bar with the hand in order to raise the pitch of the strings.";
        final String WhammyPushName = "Whammy Push";
        final String WhammyPushDesc = "Push down the whammy bar with the hand in order to drop the pitch of the strings.";
        final String WhammyReleaseName = "Whammy Release";
        final String WhammyReleaseDesc = "Release the whammy bar in order to bring the pitch of the strings back to normal.";
        final String WhammyVibratoName = "Whammy Vibrato";
        final String WhammyVibratoDesc = "Vibrate the whammy bar in order to vibrate the pitch of the strings up and down.";
    }

    /**
     * {@code GuitarPlayer} holds interaction and technique names and descriptions common to all or most guitar players.
     */
    public
    interface GuitarPlayer
    extends LutePlayer
    {
        final String PickSlideName = "Pick Slide";
        final String PickSlideDesc = "Slide the edge of the pick on the string toward or away from the fretboard.";
        final String SlideReleaseName = "Release (Slide)";
        final String SlideReleaseDesc = "Release the slide from the string.";
        final String SlideSlideName = "Slide (Slide)";
        final String SlideSlideDesc = "Move the slide along the fretboard from one fret to another.";
        final String SlideTapName = "Tap (Slide)";
        final String SlideTapDesc = "Tap the string with the slide allowing the string to vibrate.";
        final String SlideTouchName = "Touch (Slide)";
        final String SlideTouchDesc = "Touch the string with the slide.";
        final String SlideVibratoName = "Vibrato (Slide)";
        final String SlideVibratoDesc = "Vibrate the slide on the string slightly back and forth at the fret position.";
    }

    /**
     * {@code Instrument} holds all instrument names.
     */
    public
    interface Instrument
    {
        final String AcousticGrandPiano = "acoustic grand piano";
        final String AcousticGuitar = "acoustic guitar";
        final String ElectricGuitar = "electric guitar";
        final String ElectricPiano = "electric piano";
        final String GrandPiano = "grand piano";
        final String Guitar = "guitar";
        final String NylonGuitar = "nylon guitar";
        final String Piano = "piano";
    }

    /**
     * {@code KeyboardPlayer} holds interaction and technique names and descriptions common to all or most keyboard players.
     */
    public
    interface KeyboardPlayer
    extends Performer
    {
        final String PositionDesc = "Move the hand along the keyboard from its current position to the new position.";
        final String PressDesc = "Press and hold the key with the finger.";
        final String ReleaseDesc = "Release the finger off of the key in order to mute the string.";
        final String SlideDesc = "Slide the finger along the keyboard from one key to another sounding all the keys in between.";
        final String TremoloDesc = "Press the key multiple times rapidly with the finger.";
        final String TrillDesc = "Trill the key with the finger.";
    }

    /**
     * {@code LutePlayer} holds interaction and technique names and descriptions common to all or most lute players.
     */
    public
    interface LutePlayer
    extends Performer
    {
        final String PluckDesc = "Pluck the string with the finger.";
        final String PositionDesc = "Move the hand along the fretboard from one position to another.";
        final String PressDesc = "Press and hold the fret with the finger without allowing the string to vibrate.";
        final String ReleaseDesc = "Release the string lightly, where the finger currently is, without allowing the string to vibrate.";
        final String SlideDesc = "Slide the finger along the fretboard from one fret to another.";
        final String TouchDesc = "Touch the string with the finger.";
        final String TremoloDesc = "Pluck the string multiple times rapidly with the finger.";
        final String TrillDesc = "Rapidly trill the string point multiple times with the finger to vibrate the string.";

        final String BarName = "Bar";
        final String BarDesc = "Press and hold the fret with the inside of the finger phalanges.";
        final String BendName = "Bend";
        final String BendDesc = "Bend the string with the finger at the already pressed fret.";
        final String BendReleaseName = "Bend Release";
        final String BendReleaseDesc = "Release the already bent string with the finger at the already pressed fret.";
        final String HammerOnName = "Hammer-on";
        final String HammerOnDesc = "Press and hold the fret with the finger to vibrate the string.";
        final String HarmonicName = "Harmonic";
        final String HarmonicDesc = "Lightly touch and hold the node with the tip of the finger in order to silence the harmonic.";
        final String HarmonicBarName = "Harmonic (Bar)";
        final String HarmonicBarDesc = "Lightly touch and hold the node with the inside of the bar finger phalanges in order to silence the harmonic.";
        final String MuteName = "Mute";
        final String MuteDesc = "Touch the string with the finger in order to silence it.";
        final String PalmMuteName = "Palm Mute";
        final String PalmMuteDesc = "Press the side of the palm against the strings near the bridge in order to dampen their sound.";
        final String PalmReleaseName = "Palm Release";
        final String PalmReleaseDesc = "Release the side of the palm from the strings near the bridge in order to undampen their sound.";
        final String PickName = "Pick";
        final String PickDesc = "Pluck the string with the pick.";
        final String PinchHarmonicName = "Pinch Harmonic";
        final String PinchHarmonicDesc = "Pluck the string with the pick and quickly touch it with the corner of the thumb at the node to sound the harmonic.";
        final String PullOffName = "Pull-off";
        final String PullOffDesc = "Pull the finger off of the already pressed fret and let the string vibrate.";
        final String BarReleaseName = "Release (Bar)";
        final String BarReleaseDesc = "Release the frets that are currently barred with the inside of the finger phalanges.";
        final String BarSlideName = "Slide (Bar)";
        final String BarSlideDesc = "Slide the bar finger along the fretboard from one fret to another.";
        final String TapName = "Tap";
        final String TapDesc = "Tap and hold the fret with the finger and let the string vibrate.";
        final String TapHarmonicName = "Tap Harmonic";
        final String TapHarmonicDesc = "Rapidly tap the string once at the node with the finger in order to sound the harmonic.";
        final String TapTrillName = "Tap Trill";
        final String TapTrillDesc = "Rapidly tap the string multiple times with the finger.";
        final String PickTremoloName = "Tremolo (Pick)";
        final String PickTremoloDesc = "Rapidly pluck the string multiple times with the pick.";
        final String VibratoDesc = "Vibrate the string at the fret with the finger.";
        final String BarVibratoName = "Vibrato (Bar)";
        final String BarVibratoDesc = "Vibrate the strings at the fret with the bar finger.";
    }

    /**
     * {@code Performer} holds interaction and technique names and descriptions common to all or most instrument performers.
     */
    public
    interface Performer
    {
        final String PluckName = "Pluck";
        final String PluckDesc = "Pluck the instrument part with the body part.";
        final String PositionName = "Position";
        final String PositionDesc = "Move the body part across the instrument parts from one position to another.";
        final String PressName = "Press";
        final String PressDesc = "Press and hold the instrument part with the body part.";
        final String ReleaseName = "Release";
        final String ReleaseDesc = "Release the body part off of the the instrument part.";
        final String SlideName = "Slide";
        final String SlideDesc = "Slide the body part along the instrument parts from one part to another engaging all the parts in between.";
        final String TouchName = "Touch";
        final String TouchDesc = "Touch the instrument part with the body part.";
        final String TremoloName = "Tremolo";
        final String TremoloDesc = "Engage the instrument part multiple times rapidly with the body part.";
        final String TrillName = "Trill";
        final String TrillDesc = "Trill the instrument part multiple times rapidly with the body part.";
        final String VibratoName = "Vibrato";
        final String VibratoDesc = "Vibrate the instrument part with the body part.";
    }

    /**
     * {@code Piano} holds all names related to piano instruments.
     */
    public
    interface Piano
    {
        final String SoftPedal = "soft";
        final String SostenutoPedal = "sostenuto";
        final String SustainPedal = "sustain";
    }

    /**
     * {@code PianoPlayer} holds interaction and technique names and descriptions common to all or most piano players.
     */
    public
    interface PianoPlayer
    extends KeyboardPlayer
    {
        final String PluckDesc = "Pluck the piano string with the finger to vibrate the string.";

        final String HoldReleaseName = "Hold Release";
        final String HoldReleaseDesc = "Release the finger partially off of the the piano key without silencing the tune.";
        final String PedalLockName = "Pedal Lock";
        final String PedalLockDesc = "Lock the piano pedal in the engaged position with the foot.";
        final String PedalPressName = "Pedal Press";
        final String PedalPressDesc = "Press the piano pedal with the foot to engage it.";
        final String PedalReleaseName = "Pedal Release";
        final String PedalReleaseDesc = "Release the foot off of the piano pedal.";
        final String PedalUnlockName = "Pedal Unlock";
        final String PedalUnlockDesc = "Unlock the piano pedal with the foot.";
    }
}