# Demo
基础类
it.scrollRoot.apply {
val redDot = CustomRedDot(this@TestActivity).apply {
maxHeight = ScreenUtils.dp2px(24f).toInt()
setPadding(
ScreenUtils.dp2px(6f).toInt(),
ScreenUtils.dp2px(4f).toInt(),
ScreenUtils.dp2px(6f).toInt(),
ScreenUtils.dp2px(4f).toInt()
)
 text = "热门闷闷"
}
addView(redDot)



git技巧
保存更改然后切换分支在拿出更改
1. git stash // 暂存更改
2. git checkout  fenzhi
3. git stash pop // 拿出更改