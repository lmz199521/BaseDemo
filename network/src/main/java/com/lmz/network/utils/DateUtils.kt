package com.lmz.network.utils

import android.icu.util.Calendar
import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects

/**
 * describe:
 * Date:2024/12/18
 * Author:lmz
 */
public const val DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss"
const val FULL_TIME = "yyyy-MM-dd HH:mm:ss:SSS"
const val YEAR_MONTH_DAY = "yyyy-MM-dd"
const val YEAR_MONTH_DAY_CN = "yyyy年MM月dd日"
const val HOUR_MINUTE_SECOND = "HH:mm:ss"
const val HOUR_MINUTE_SECOND_CN = "HH时mm分ss秒"
const val YEAR = "yyyy"
const val MONTH = "MM"
const val DAY = "dd"
const val HOUR = "HH"
const val MINUTE = "mm"
const val SECOND = "ss"
const val MILLISECOND = "SSS"
const val YESTERDAY = "昨天"
const val TODAY = "今天"
const val TOMORROW = "明天"
const val MONDAY = "周一"
const val TUESDAY = "周二"
const val WEDNESDAY = "周三"
const val THURSDAY = "周四"
const val FRIDAY = "周五"
const val SATURDAY = "周六"
const val SUNDAY = "周日"
val WEEK_DAYS = arrayOf(

    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY
)

class DateUtils {

    companion object {

        /**
         *  获取标准时间
         *   例如 2021-07-01 10:35:53
         */
        @JvmStatic
        fun getDateTime(): String {
            return SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.CHINESE).format(Date())
        }

        /**
         *  获取完整时间  包含秒
         *   例如 2021-07-01 10:37:00.748
         */
        @JvmStatic
        fun getFullDateTime(): String {
            return SimpleDateFormat(FULL_TIME, Locale.CHINESE).format(Date())
        }

        /**
         *  获取今天的年月日
         *  例如 2021-07-01
         */
        @JvmStatic
        fun getTheYearMonthAndDay(): String {
            return SimpleDateFormat(YEAR_MONTH_DAY, Locale.CHINESE).format(Date())
        }

        /**
         *  获取今天的年月日
         *   例如 2021年07月01号
         */
        @JvmStatic
        fun getTheYearMonthAndDayCN(): String {
            return SimpleDateFormat(YEAR_MONTH_DAY_CN, Locale.CHINESE).format(Date())
        }

        /**
         * 获取年月日
         * @param delimiter 分隔符
         * @return 例如 2021年07月01号
         */
        @JvmStatic
        fun getTheYearMonthAndDayDelimiter(delimiter: CharSequence): String {
            return SimpleDateFormat(
                YEAR + delimiter + MONTH + delimiter + DAY,
                Locale.CHINESE
            ).format(Date())
        }

        /**
         * 获取时分秒
         * 例如：10：41：42
         */
        @JvmStatic
        fun getHoursMinutesAndSeconds(): String {
            return SimpleDateFormat(HOUR_MINUTE_SECOND, Locale.CHINESE).format(Date())
        }

        /**
         * 获取时分秒
         * 例如：10时38分50秒
         */
        @JvmStatic
        fun getHoursMinutesAndSecondsCN(): String {
            return SimpleDateFormat(HOUR_MINUTE_SECOND_CN, Locale.CHINESE).format(Date())
        }

        /**
         * 获取时分秒
         * @param delimiter 分隔符
         * @return 例如 2021/07/01
         */
        @JvmStatic
        fun getHoursMinutesAndSecondsDelimiter(delimiter: CharSequence): String {
            return SimpleDateFormat(
                HOUR + delimiter + MINUTE + delimiter + SECOND,
                Locale.CHINESE
            ).format(Date())
        }

        @JvmStatic
        fun getYear(): String {
            return SimpleDateFormat(YEAR, Locale.CHINESE).format(Date())
        }

        @JvmStatic
        fun getMonth(): String {
            return SimpleDateFormat(MONTH, Locale.CHINESE).format(Date())
        }

        @JvmStatic
        fun getDay(): String {
            return SimpleDateFormat(DAY, Locale.CHINESE).format(Date())
        }

        @JvmStatic
        fun getHours(): String {
            return SimpleDateFormat(HOUR, Locale.CHINESE).format(Date())
        }

        @JvmStatic
        fun getMinutes(): String {
            return SimpleDateFormat(MINUTE, Locale.CHINESE).format(Date())
        }

        @JvmStatic
        fun getSeconds(): String {
            return SimpleDateFormat(SECOND, Locale.CHINESE).format(Date())
        }

        @JvmStatic
        fun getMillisecond(): String {
            return SimpleDateFormat(MILLISECOND, Locale.CHINESE).format(Date())
        }

        /**
         *  获取时间戳
         */
        @JvmStatic
        fun getTimeStamp(): Long {
            return System.currentTimeMillis()
        }

        /**
         * 获取第二天凌晨的时间戳
         */
        @JvmStatic
        fun getMillisNextEarlyMorning(): Long {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, 1)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.SECOND, 0)
            return cal.timeInMillis
        }

        /**
         *  将时间转化为时间戳
         */
        @JvmStatic
        fun dateToStamp(time: String): Long {
            val sdf = SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.CHINESE)
            var date: Date? = null
            try {
                date = sdf.parse(time)
            } catch (e: Exception) {
                e.stackTrace
            }

            return date?.time ?: 0
        }

        @JvmStatic
        fun stampToDate(timeMillis: Long): String {
            return SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.CHINESE).format(timeMillis)
        }

        /**
         * 获取今天的星期几
         */
        @JvmStatic
        fun getTodayOfWeek(): String {
            val cal = Calendar.getInstance()
            cal.time = Date()
            var index = cal.get(Calendar.DAY_OF_WEEK) - 1
            if (index < 0) {
                index = 0
            }
            return WEEK_DAYS[index]
        }

        /**
         * 根据输入的日期时间计算是星期几
         *
         * @param dateTime 例如 2021-06-20
         * @return 例如 星期日
         */
        @JvmStatic
        fun getWeek(dateTime: String): String {
            val cal = Calendar.getInstance()
            if (TextUtils.isEmpty(dateTime)) {
                cal.time = Date(System.currentTimeMillis())
            } else {
                val sdf = SimpleDateFormat(YEAR_MONTH_DAY, Locale.CHINESE)
                var date: Date? = null
                try {
                    date = sdf.parse(dateTime)
                } catch (e: Exception) {
                    e.stackTrace
                }

                if (date != null) {
                    cal.time = Date(date.time)
                }
            }
            return WEEK_DAYS[cal.get(Calendar.DAY_OF_WEEK) - 1]
        }

        /**
         * 获取输入日期的明天
         *
         * @param date 例如 2021-07-01
         * @return 例如 2021-07-02
         */
        @JvmStatic
        fun getYesterday(date: Date): String {
            var mDate = date
            val cal = Calendar.getInstance()
            cal.time = mDate
            cal.add(Calendar.DATE, -1)
            mDate = cal.time
            return SimpleDateFormat(YEAR_MONTH_DAY, Locale.CHINESE).format(mDate)
        }

        @JvmStatic
        fun getTomorrow(date: Date): String {
            var mDate = date
            val cal = Calendar.getInstance()
            cal.time = mDate
            cal.add(Calendar.DATE, +1)
            mDate = cal.time
            return SimpleDateFormat(YEAR_MONTH_DAY, Locale.CHINESE).format(mDate)
        }

        /**
         * 根据年月日计算是星期几并与当前日期判断  非昨天、今天、明天 则以星期显示
         *
         * @param dateTime 例如 2021-07-03
         * @return 例如 星期六
         */
        @JvmStatic
        fun getDayInfo(dateTime: String): String {
            val yesterDay = getYesterday(Date())
            val today = getTheYearMonthAndDay()
            val tomorrow = getTomorrow(Date())
            var dayInfo: String?
            if (dateTime.equals(yesterDay)) {
                dayInfo = YESTERDAY
            } else if (dateTime.equals(today)) {
                dayInfo = TODAY
            } else if (dateTime.equals(tomorrow)) {
                dayInfo = TOMORROW
            } else {
                dayInfo = getWeek(dateTime)
            }
            return dayInfo
        }

        /**
         * 获取本月天数
         *
         * @return 例如 31
         */
        @JvmStatic
        fun getCurrentMonthDays(): Int {
            val cal = Calendar.getInstance()
            // 把日期设置为当月的第一天
            cal.set(Calendar.DATE, 1)
            //日期回滚一天，也就是最后一天
            cal.roll(Calendar.DATE, -1)
            return cal.get(Calendar.DATE)
        }

        /**
         * 获得指定月的天数
         *
         * @param year  例如 2021
         * @param month 例如 7
         * @return 例如 31
         */
        @JvmStatic
        fun getMonthDays(year: Int, month: Int): Int {
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            // 把日期设置为当月的第一天
            cal.set(Calendar.DATE, 1)
            //日期回滚一天，也就是最后一天
            cal.roll(Calendar.DATE, -1)
            return cal.get(Calendar.DATE)
        }

    }
}