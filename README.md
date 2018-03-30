
# react-native-kakao-link

## Getting started

`$ npm install react-native-kakao-link --save`

### Mostly automatic installation

`$ react-native link react-native-kakao-link`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-kakao-link` and add `RNKakaoLink.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNKakaoLink.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNKakaoLinkPackage;` to the imports at the top of the file
  - Add `new RNKakaoLinkPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-kakao-link'
  	project(':react-native-kakao-link').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-kakao-link/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-kakao-link')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNKakaoLink.sln` in `node_modules/react-native-kakao-link/windows/RNKakaoLink.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Kakao.Link.RNKakaoLink;` to the usings at the top of the file
  - Add `new RNKakaoLinkPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNKakaoLink from 'react-native-kakao-link';

// TODO: What to do with the module?
RNKakaoLink;
```
  