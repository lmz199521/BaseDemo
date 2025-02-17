package com.lmz.widght

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.icu.text.DecimalFormat
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextPaint
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.annotation.ColorInt
import com.example.Tools.ScreenUtils
import com.lmz.widget.R
import java.math.RoundingMode
import java.util.Currency
import java.util.Locale
import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.math.max

/**
 * describe:
 * Date:2025/1/12
 * Author:lmz
 */
class AmountTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    companion object {
        // 使用正则去验证传入的金额是否有效，防止格式化非数字意外的内容
        private const val NUMBER_CONSTRAINS = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$"
        private const val MIN_PADDING = 2f
    }

    private var totalWidth = 0
    private var totalHeight = 0
    private var textPaintRoomSize = 0f

    private val symbolSection by lazy { Section() }
    private val integerSection by lazy { Section() }
    private val decimalSection by lazy { Section() }
    private val positiveNegativeSection by lazy { Section() }

    private val linePaint by lazy {
        Paint()
    }

    private val textPaint by lazy { TextPaint(Paint.ANTI_ALIAS_FLAG) }

    init {
        initView(context, attrs, defStyleAttr)
    }

    /**
     *  设置金额
     */
    private var amount = 0.0
        set(value) {
            field = if (checkAmountValid("$value")) value else 0.0
            requestLayout()
        }

    @ColorInt
    var textColor = Color.parseColor("#8A000000")
        set(value) {
            field = value
            integerSection.color = value
            requestLayout()
        }

    /**
     *  设置文字大小
     */
    private var textSize = ScreenUtils.dp2px(14f)
        set(value) {
            field = value
            integerSection.textSize = value
            requestLayout()
        }

    /**
     *  是否显示正负号
     */
    private var positiveNegativeEnable = false
        set(value) {
            field = value
            requestLayout()
        }

    private var positiveNegativePadding = 4f
        set(value) {
            field = value
            requestLayout()
        }

    private var positiveNegativeTextSize = ScreenUtils.dp2px(14f)
        set(value) {
            field = value
            positiveNegativeSection.textSize = value
            requestLayout()
        }

    private var positiveNegativeTextColor = textColor
        set(value) {
            field = value
            positiveNegativeSection.color = value
            postInvalidate()
        }

    /**
     *  是否使用千分符
     */
    private var groupingUsed = false
        set(value) {
            field = value
            requestLayout()
        }

    /**
     *  设置四舍五入模式 默认只舍不入
     */
    private var roundingMode = RoundingMode.DOWN
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * 货币符号, 默认使用 ￥ 符号
     * 这里做个适配，直接写 ￥ 符号，在国产部分手机上 ￥ 符号中间的横杠会显示一条
     */
    var symbol = "${fromHtml("&yen")}"
        set(value) {
            field = value
            symbolSection.text = value
            requestLayout()
        }

    /**
     * 符号位置
     */
    var symbolGravity = Gravity.START
        set(value) {
            field = value
            postInvalidate()
        }

    @ColorInt
    var symbolTextColor = textColor
        set(value) {
            field = value
            symbolSection.color = value
            postInvalidate()
        }

    /**
     *  货币符号字体大小
     */
    var symbolTextSize = textSize
        set(value) {
            field = value
            symbolSection.textSize = value
            requestLayout()
        }

    var symbolPadding = 4f
        set(value) {
            field = value
            requestLayout()
        }

    /**
     *  设置保留的小数位数
     */
    var decimalDigits = 0
        set(value) {
            field = value
            requestLayout()
        }

    /**
     *  小数位置 支持top和bottom
     */
    var decimalGravity = Gravity.BOTTOM
        set(value) {
            field = value
            postInvalidate()
        }

    /**
     *  小数点与数字之间的间距
     */
    var decimalPadding = 4f
        set(value) {
            field = value
            requestLayout()
        }

    /**
     *  小数位文本大小
     */
    var decimalTextSize = textSize
        set(value) {
            field = value
            decimalSection.textSize = value
            requestLayout()
        }

    /**
     *  小数位文本颜色
     */
    var decimalTextColor = textColor
        set(value) {
            field = value
            decimalSection.color = value
            postInvalidate()
        }

    /**
     *  删除线
     */
    var strikeThroughLineEnable = false
        set(value) {
            field = value
            postInvalidate()
        }

    var strikeThroughLineColor = Color.BLACK
        set(value) {
            field = value
            postInvalidate()
        }

    var strikeThroughSize = 1f
        set(value) {
            field = value
            requestLayout()
        }


    /**
     *  下划线
     */
    var underlineEnable = false
        set(value) {
            field = value
            postInvalidate()
        }

    var underlineColro = Color.BLACK
        set(value) {
            field = value
            postInvalidate()
        }

    var underlineSize = 1f
        set(value) {
            field = value
            requestLayout()
        }




    private fun initView(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.AmountTextView,
            defStyleAttr,
            0
        )



        try {
            // positive negative symbol
            positiveNegativeEnable = typedArray.getBoolean(
                R.styleable.AmountTextView_positiveNegative,
                positiveNegativeEnable
            )
            positiveNegativePadding = typedArray.getDimensionPixelSize(
                R.styleable.AmountTextView_positiveNegativePadding,
                positiveNegativePadding.toInt()
            ).toFloat()
            positiveNegativeSection.textSize = typedArray.getDimension(
                R.styleable.AmountTextView_positiveNegativeTextSize,
                positiveNegativeTextSize
            )
            positiveNegativeSection.color = typedArray.getColor(
                R.styleable.AmountTextView_positiveNegativeColor,
                positiveNegativeTextColor
            )

            // symbol
            symbol = typedArray.getString(R.styleable.AmountTextView_symbol) ?: symbol
            symbolGravity =
                typedArray.getInt(R.styleable.AmountTextView_symbolGravity, symbolGravity)
            symbolPadding = typedArray.getDimensionPixelSize(
                R.styleable.AmountTextView_symbolPadding,
                symbolPadding.toInt()
            ).toFloat()
            symbolSection.textSize =
                typedArray.getDimension(R.styleable.AmountTextView_symbolSize, symbolTextSize)
            symbolSection.color =
                typedArray.getColor(R.styleable.AmountTextView_symbolColor, symbolTextColor)

            // amount
            if (typedArray.hasValue(R.styleable.AmountTextView_android_text)) {
                val text = typedArray.getString(R.styleable.AmountTextView_android_text) ?: "0"
                amount = if (checkAmountValid(text)) text.toDouble() else 0.0
            }

            integerSection.textSize =
                typedArray.getDimension(R.styleable.AmountTextView_android_textSize, textSize)
            integerSection.color =
                typedArray.getColor(R.styleable.AmountTextView_android_textColor, textColor)
            groupingUsed =
                typedArray.getBoolean(R.styleable.AmountTextView_groupingUsed, groupingUsed)
            roundingMode =
                getRoundingMode(typedArray.getInt(R.styleable.AmountTextView_roundingMode, 0))

            // decimal
            decimalDigits =
                typedArray.getInteger(R.styleable.AmountTextView_decimalDigits, decimalDigits)
            decimalGravity =
                typedArray.getInt(R.styleable.AmountTextView_decimalGravity, decimalGravity)
            decimalPadding = typedArray.getDimensionPixelSize(
                R.styleable.AmountTextView_decimalPadding,
                decimalPadding.toInt()
            ).toFloat()
            decimalSection.textSize = typedArray.getDimension(
                R.styleable.AmountTextView_decimalTextSize,
                decimalTextSize
            )
            decimalSection.color = typedArray.getColor(
                R.styleable.AmountTextView_decimalTextColor,
                decimalTextColor
            )

            // style
            val textStyle = typedArray.getInt(
                R.styleable.AmountTextView_android_textStyle,
                Typeface.DEFAULT.style
            )
            textPaint.typeface = Typeface.defaultFromStyle(textStyle)

            // 删除线
            strikeThroughLineEnable = typedArray.getBoolean(
                R.styleable.AmountTextView_strikeThroughLine,
                strikeThroughLineEnable
            )
            strikeThroughLineColor = typedArray.getColor(
                R.styleable.AmountTextView_strikeThroughLineColor,
                strikeThroughLineColor
            )
            strikeThroughSize = typedArray.getDimensionPixelSize(
                R.styleable.AmountTextView_strikeThroughLineSize,
                strikeThroughSize.toInt()
            ).toFloat()

            //下划线
            underlineEnable =
                typedArray.getBoolean(R.styleable.AmountTextView_underLine, underlineEnable)
            underlineColro =
                typedArray.getColor(R.styleable.AmountTextView_underLineColor, underlineColro)
            underlineSize = typedArray.getDimensionPixelSize(
                R.styleable.AmountTextView_underLineSize,
                underlineSize.toInt()
            ).toFloat()


        } finally {
            typedArray.recycle()
        }

        if (strikeThroughLineEnable || underlineEnable) {
            linePaint.apply {
                isDither = true
                isAntiAlias = true
                style = Paint.Style.FILL_AND_STROKE
            }
        }

    }

    // 设置当前语言,货币符号会跟着国家的变化而变化
    fun setCurrency(locale: Locale) {
        val currency = Currency.getInstance(locale)
        this.symbol = currency.symbol
    }

    /**
     *  检查金额是否有效
     */
    private fun checkAmountValid(text: String): Boolean {
        return Pattern.compile(NUMBER_CONSTRAINS).matcher(text).matches()
    }

    private fun getRoundingMode(mode: Int): RoundingMode {
        return when (mode) {
            1 -> RoundingMode.UP
            2 -> RoundingMode.DOWN
            3 -> RoundingMode.CEILING
            4 -> RoundingMode.FLOOR
            5 -> RoundingMode.HALF_UP
            6 -> RoundingMode.HALF_DOWN
            7 -> RoundingMode.HALF_EVEN
            8 -> RoundingMode.UNNECESSARY
            else -> RoundingMode.DOWN

        }
    }

    private fun getMinPadding(padding: Int): Int {
        val density = resources.displayMetrics.density
        return if (padding == 0) (MIN_PADDING * density).toInt() else padding
    }

    private fun getMinVerticalPadding(padding: Int): Int {
        val maxTextSize = max(
            positiveNegativeSection.textSize,
            max(symbolSection.textSize, max(integerSection.textSize, decimalSection.textSize))
        )
        textPaint.textSize = maxTextSize
        val maximumDistanceLowestGlyph = textPaint.fontMetrics.bottom
        return if (padding < maximumDistanceLowestGlyph) maximumDistanceLowestGlyph.toInt() else padding
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setPadding(
            getMinPadding(paddingStart), getMinVerticalPadding(paddingTop),
            getMinPadding(paddingEnd), getMinVerticalPadding(paddingBottom)
        )
        createTextFromAmount()
        calculateBounds(widthMeasureSpec, heightMeasureSpec)
        calculatePositions()
        setMeasuredDimension(totalWidth, totalHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawSection(canvas, positiveNegativeSection)
        drawSection(canvas, symbolSection)
        drawSection(canvas, integerSection)
        drawSection(canvas, decimalSection)
        drawDecorationLine(canvas)
    }

    private fun drawSection(canvas: Canvas, section: Section) {
        textPaint.textSize = section.textSize
        textPaint.color = section.color
        canvas.drawText(
            section.text,
            section.x - textPaintRoomSize.times(2f),
            section.y - textPaintRoomSize.div(2f),
            textPaint
        )
    }

    private fun drawDecorationLine(canvas: Canvas) {
        val lineMaxWidth =
            positiveNegativeSection.width + positiveNegativePadding + symbolSection.width + symbolPadding + integerSection.width + decimalPadding + decimalSection.width

        //绘制删除线
        if (strikeThroughLineEnable) {
            linePaint.color = strikeThroughLineColor
            linePaint.strokeWidth = strikeThroughSize
            val strikeThroughLineY = totalHeight.div(2f)
            canvas.drawLine(
                paddingStart.toFloat(),
                strikeThroughLineY,
                lineMaxWidth,
                strikeThroughLineY,
                linePaint
            )
        }

        // 绘制下划线
        if (underlineEnable) {
            linePaint.color = underlineColro
            linePaint.strokeWidth = underlineSize
            val underlineLineY = (totalHeight - paddingBottom).toFloat()
            canvas.drawLine(
                paddingStart.toFloat(),
                underlineLineY,
                lineMaxWidth,
                underlineLineY,
                linePaint
            )
        }

    }

    /**
     * @return 返回格式化后的金额文本
     */
    private fun getAmountText(): String {
        val positiveNegative = if (positiveNegativeEnable) if (amount > -1) "+" else "-" else ""
        val amountFormat = formatAmount(abs(amount), decimalDigits, groupingUsed, roundingMode)
        val amountValue = amountFormat.split(".").firstOrNull() ?: "0"
        val decimalValue = (amountFormat.split(".").lastOrNull() ?: "0").toLong()
        val decimalFormat = if (decimalValue > 0) ".${decimalValue}" else ""
        return positiveNegative + symbol + amountValue + decimalFormat
    }

    private fun createTextFromAmount() {
        val positiveNegative = if (positiveNegativeEnable) if (amount > -1) "+" else "-" else ""
        positiveNegativeSection.text = positiveNegative
        symbolSection.text = symbol
        // 奖金额格式化，添加千分符，进行四舍五入计算
        val amountFormat = formatAmount(abs(amount), decimalDigits, groupingUsed, roundingMode)
        integerSection.text = amountFormat.split(".").firstOrNull() ?: "0"
        val decimalValue = (amountFormat.split(".").lastOrNull() ?: "0").toLong()
        val decimalFormat = if (decimalDigits > 0) ".${decimalValue}" else ""
        decimalSection.text = decimalFormat
        decimalPadding = if (decimalValue > 0) decimalPadding else 0f
    }

    private fun calculateBounds(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        symbolSection.calculateRounds(textPaint)
        integerSection.calculateRounds(textPaint)
        decimalSection.calculateRounds(textPaint)
        positiveNegativeSection.calculateRounds(textPaint)
        decimalSection.calculateNumbersHeight(textPaint)
        integerSection.calculateNumbersHeight(textPaint)
        positiveNegativeSection.calculateNumbersHeight(textPaint)

        when (widthMode) {
            MeasureSpec.EXACTLY -> totalWidth = widthSize
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                val positiveNegativeWidth =
                    if (positiveNegativeEnable) positiveNegativeSection.width + positiveNegativePadding.toInt() else 0
                val symbolWidth = symbolSection.width + symbolPadding
                val amountWidth = integerSection.width
                val decimalWidth = decimalSection.width + decimalPadding
                totalWidth =
                    (paddingStart + positiveNegativeWidth + symbolWidth + amountWidth + decimalWidth + paddingEnd).toInt()
            }
        }

        when (heightMode) {
            MeasureSpec.EXACTLY -> totalHeight = heightSize
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                totalHeight = paddingTop + paddingBottom + max(
                    integerSection.height,
                    max(decimalSection.height, symbolSection.height)
                )
            }
        }
    }

    private fun calculatePositions() {
        val positiveNegativeWidth =
            if (positiveNegativeEnable) positiveNegativeSection.width + positiveNegativePadding.toInt() else 0
        if (positiveNegativeEnable) {
            positiveNegativeSection.apply {
                x = paddingStart
                y = calculatePositiveNegativeX()
            }
        } else {
            positiveNegativeSection.apply {
                x = 0
                y = 0
            }
        }

        symbolSection.x = calculateSymbolX(positiveNegativeWidth)
        symbolSection.y = calculateSymbolY()

        integerSection.x = calculateAmountX(positiveNegativeWidth)
        integerSection.y = totalHeight - paddingBottom

        decimalSection.x = calculateDecimalX(positiveNegativeWidth)
        decimalSection.y = calculateDecimalY()


    }


    private fun calculatePositiveNegativeX(): Int {
        val textPaintHeight = textPaint.fontMetrics.bottom - textPaint.fontMetrics.top
        return (totalHeight.div(2f) + textPaintHeight.div(2f) - textPaint.fontMetrics.bottom - textPaintRoomSize.div(
            2f
        )).toInt()
    }

    private fun calculateSymbolX(positiveNegativeWidth: Int): Int {
        return if (symbolGravity == Gravity.END || symbolGravity == Gravity.END or Gravity.TOP
            || symbolGravity == Gravity.END or Gravity.BOTTOM
        ) {
            (paddingStart + positiveNegativeWidth + integerSection.width + decimalPadding + decimalSection.width + decimalPadding).toInt()
        } else paddingStart + positiveNegativeWidth
    }

    private fun calculateSymbolY(): Int {
        return when (symbolGravity) {
            Gravity.START or Gravity.TOP,
            Gravity.END or Gravity.TOP -> paddingTop + symbolSection.height

            Gravity.START or Gravity.BOTTOM, Gravity.END or Gravity.BOTTOM -> totalHeight - paddingBottom
            else -> {
                val maxHeight = max(
                    positiveNegativeSection.height,
                    max(symbolSection.height, max(integerSection.height, decimalSection.height))
                )
                totalHeight.div(2) + maxHeight.div(2) - textPaintRoomSize.div(2f).toInt()
            }
        }
    }

    private fun calculateAmountX(positiveNegativeWidth: Int): Int {
        return if (symbolGravity == Gravity.END
            || symbolGravity == Gravity.END or Gravity.TOP
            || symbolGravity == Gravity.END or Gravity.BOTTOM
        ) {
            paddingStart + positiveNegativeWidth
        } else {
            (paddingStart + positiveNegativeWidth + symbolSection.width + symbolPadding).toInt()
        }
    }

    private fun calculateDecimalX(positiveNegativeWidth: Int): Int {
        return if (symbolGravity == Gravity.START
            || symbolGravity == Gravity.START or Gravity.TOP
            || symbolGravity == Gravity.START or Gravity.BOTTOM
        ) {
            paddingStart + positiveNegativeWidth + symbolSection.width + symbolPadding.toInt() + integerSection.width + decimalPadding.toInt()
        } else if (symbolGravity == Gravity.END
            || symbolGravity == Gravity.END or Gravity.TOP
            || symbolGravity == Gravity.END or Gravity.BOTTOM
        ) {
            paddingStart + positiveNegativeWidth + integerSection.width + decimalPadding.toInt()
        } else {
            paddingStart + positiveNegativeWidth + symbolSection.width + symbolPadding.toInt() + integerSection.width + decimalPadding.toInt()
        }
    }

    private fun calculateDecimalY(): Int {
        val baseline =
            if (groupingUsed && abs(amount) > 1000) textPaint.fontMetrics.descent.toInt() else 0
        return if (decimalGravity == Gravity.TOP) paddingTop + decimalSection.height + baseline else totalHeight - paddingBottom
    }

    private fun fromHtml(content: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(content)
        }
    }

    private fun formatAmount(
        number: Number, decimalDigits: Int = 0, groupingUsed: Boolean = true,
        rounding: RoundingMode = RoundingMode.DOWN
    ): String {
        return DecimalFormat.getNumberInstance().apply {
            maximumFractionDigits = decimalDigits
            isGroupingUsed = groupingUsed
            roundingMode = rounding.ordinal
        }.format(number)
    }

    private inner class Section {
        var x = 0
        var y = 0
        var bounds: Rect = Rect()
        var text = ""
        var textSize = 0f
        var color = Color.BLACK
        var width = 0
        var height = 0

        fun calculateRounds(paint: TextPaint) {
            paint.textSize = textSize
            paint.getTextBounds(text, 0, text.length, bounds)
            width = bounds.width()
            height = bounds.height()
        }

        fun calculateNumbersHeight(paint: TextPaint) {
            val numbers = text.replace("[^0-9]", "")
            paint.textSize = textSize
            paint.getTextBounds(numbers, 0, numbers.length, bounds)
            height = bounds.height()
        }
    }
}