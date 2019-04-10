package ktfrc.robot.sensors

import edu.wpi.first.networktables.*
import edu.wpi.first.wpilibj.PIDSource
import edu.wpi.first.wpilibj.PIDSourceType

/**
 * A wrapper class for limelight information from the network table.
 */
class Limelight: PIDSource, TableEntryListener {
    /*
     * Coefficients and exponents to help find the distance of a target
     * Each equation is in the form ax^b where a is the coefficient
     * (Coeff) of the equation and b is the exponent (Exp). x is the
     * variable, either vertical length (vert), horizontal length (horiz),
     * or area (area).
     */
    //TODO Move to companion obj v
    private val vertCoeff = 111.0
    private val vertExp = -0.948
    private val nt = NetworkTableInstance.getDefault().getTable("limelight-merc")

    @get:Synchronized
    var numTargets: Double = nt.getEntry("tv").getDouble(0.0)
        private set

    @get:Synchronized
    var targetCenterXAngle: Double = 0.0
        private set

    @get:Synchronized
    var targetCenterYAngle: Double = 0.0
        private set

    @get:Synchronized
    var targetArea: Double = 0.0
        private set

    @get:Synchronized
    var horizontalLength: Double = 0.0
        private set

    @get:Synchronized
    var verticalLength: Double = 0.0
        private set

    @get:Synchronized
    var cornerXArray: DoubleArray = doubleArrayOf()
        private set

    @get:Synchronized
    var targetAcquired: Boolean = false
        private set

    val rawVertDistance: Double
        @Synchronized get() = calcDistFromVert()

    init {
        numTargets = nt.getEntry("tv").getDouble(0.0)
        targetCenterXAngle = nt.getEntry("tx").getDouble(0.0)
        targetCenterYAngle = nt.getEntry("ty").getDouble(0.0)
        targetArea = nt.getEntry("ta").getDouble(0.0)
        horizontalLength = nt.getEntry("thor").getDouble(0.0)
        verticalLength = nt.getEntry("tvert").getDouble(0.0)
        targetAcquired = nt.getEntry("tv").getDouble(0.0) != 0.0
        cornerXArray = nt.getEntry("tcornx").getDoubleArray(doubleArrayOf(0.0))
        nt.addEntryListener(this, EntryListenerFlags.kUpdate)
    }

    /**
     * @param nt    is always the limelight network table in this case
     * @param key   is the key of the entry that changed
     * @param ne    is the entry that changed
     * @param nv    is the value of the entry that changed
     * @param flags is the flag that occured which is always kUpdate in this case
     */
    override fun valueChanged(nt: NetworkTable, key: String, ne: NetworkTableEntry, nv: NetworkTableValue, flags: Int) {
        synchronized(this) {
            when (key) {
                "tx" -> targetCenterXAngle = nv.double
                "ty" -> targetCenterYAngle = nv.double
                "ta" -> targetArea = nv.double
                "tv" -> targetAcquired = nv.double != 0.0
                "tl" -> numTargets = nv.double
                "thor" -> horizontalLength = nv.double
                "tvert" -> verticalLength = nv.double
                "tcornx" -> cornerXArray = nv.doubleArray
            }
        }
    }

    @Synchronized
    override fun getPIDSourceType(): PIDSourceType = PIDSourceType.kDisplacement

    /**
     * Set the PID Source (should not be implemented)
     */
    @Synchronized
    override fun setPIDSourceType(pidST: PIDSourceType) {}

    /**
     * Get the value that PID acts on. For PIDCommand
     */
    @Synchronized
    override fun pidGet(): Double = this.targetCenterXAngle

    /**
     * Helper method for the vert-dist calculation
     *
     * @return the distance based on vertical distance
     */
    private fun calcDistFromVert(): Double = vertCoeff * Math.pow(verticalLength, vertExp) * 12.0

    /**
     * Set the LED state on the limelight.
     *
     * @param limelightLEDState the state of the LED.
     */
    fun setLEDState(limelightLEDState: LimelightLEDState) = nt.getEntry("ledMode").setNumber(limelightLEDState.value)
}

enum class LimelightLEDState(val value: Double) {
    ON(3.0),
    OFF(1.0),
    BLINKING(2.0),
    PIPELINE_DEFAULT(0.0)
}