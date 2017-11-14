# martianrun
Little copy-and-run demo of the tutorial by [William Mora](http://williammora.com/a-running-game-with-libgdx-part-1)

## 稍微总结一下自己对着抄代码也能够遇到的bugs:

|序号    |详细                                                           |
|-------|:-------------------------------------------------------------:|
|1      |在[GameStage](https://github.com/swanf/martianrun/blob/master/stages/GameStage.java)中初始化runner的时候用了WorldUtils的createGround来做参数|
|2      |在[GameStage](https://github.com/swanf/martianrun/blob/master/stages/GameStage.java)中没有看到原文中说的要把GameStage中的draw方法删掉，结果一直放在那出错|
|3      |在[Constants](https://github.com/swanf/martianrun/blob/master/utils/Constants.java)中将切割后的Atlas中的跑步外星人图像文件名alienGreen_run1打成alien**Greeen**_run1|
|4      |在[WorldUtils](https://github.com/swanf/martianrun/blob/master/utils/WorldUtils.java)的createRunner中忘记给它的高度除以2了，导致一直runner都跳在地面以上|

- [X] Finish the basic framework
- [ ] Add Menu
- [ ] Add Keyboard Support
- [ ] Add Sound
