# View Language PHPStorm Plugin

Adds code assistance in latest version of PHPStorm for [View Language](https://github.com/aherne/php-view-language-api) constructs. This includes:

- ability to navigate to a user tag definition by control-clicking on its name in your view/taglib file
- ability to suggest user tags automatically by just writing **<** and continuing with the first letters in your view/taglib file
- ability to navigate to an included view by control-clicking on the value of **file** attribute in the **<import** tag

What remains to be done:
- ability to suggest included views automatically by just writing first letters in the **file** attribute in the **<import** tag
- support for system tags

> [!NOTE]
> Plugin is still in progress and may have bugs (this is an alpha version). Once in a while you will see a harmless PHPStorm plugin error. Until I fix it, just ignore!

## Implementation steps

1. clone repository
2. navigate to created folder in terminal and run: ```./gradlew clean buildPlugin```
3. this will create a **build/distributions/IntelliJ Platform Plugin Template-1.0.0.zip** file
4. upgrade to the latest PHPStorm version
5. open PHPStorm, go to Settings > Plugins and click on the square wheel sign on top, choosing **Install plugin from disk**
6. browse for zip file created in step 2 and hit **Open** (this will install plugin on your phpstorm)
7. restart PHPStorm completely

## User steps

1. clone repository
2. upgrade to the latest PHPStorm version
3. open PHPStorm, go to Settings > Plugins and click on the square wheel sign on top, choosing **Install plugin from disk**
4. browse for **ViewLanguagePlugin.zip** file found in repository root and hit **Open** (this will install plugin on your phpstorm)
5. restart PHPStorm completely
