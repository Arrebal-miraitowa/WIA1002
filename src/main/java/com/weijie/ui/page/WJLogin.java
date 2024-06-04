package com.weijie.ui.page;

import com.weijie.core.common.Validation;
import com.weijie.core.service.UserFilterService;
import com.weijie.ui.FXTool.AnimationUtils;
import com.weijie.ui.FXTool.ResourcesLoader;
import com.weijie.ui.FXTool.SimpleButton;
import com.weijie.ui.FXTool.SimpleControl;
import com.weijie.ui.controls.WJCheckBox;
import com.weijie.ui.controls.WJMessage;
import com.weijie.ui.controls.WJStage;
import com.weijie.ui.controls.WJTextField;
import com.weijie.ui.enums.WJLevel;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Consumer;

public class WJLogin extends StackPane {

    private DoubleProperty DEFAULT_HEIGHT = new SimpleDoubleProperty(40);
    private DoubleProperty DEFAULT_WIDTH = new SimpleDoubleProperty(250);
    // 登录，注册容器
    private SignInBox signInBox = new SignInBox();
    private SignUpBox signUpBox = new SignUpBox();
    private WJMid signUpMid = new WJMid();
    //动画
    private TranslateTransition TT1 = new TranslateTransition(Duration.millis(500));
    private RotateTransition RT = new RotateTransition(Duration.millis(500));
    private ParallelTransition PT = new ParallelTransition(TT1, RT);
    private TranslateTransition TT2 = new TranslateTransition(Duration.millis(500));
    private SequentialTransition SEQ_T = new SequentialTransition(PT, TT2);
    private double translateX = -400;
    private double rotateAngle = 5;

    private WJStage wjStage;

    public WJLogin(WJStage stage) {
        wjStage = stage;
        initialize();
    }

    public WJMid getSignUpMid() {
        return signUpMid;
    }

    public SignInBox getSignInBox() {
        return signInBox;
    }

    public SignUpBox getSignUpBox() {
        return signUpBox;
    }

    private void initialize() {
        getChildren().addAll(signUpBox, signInBox, signUpMid);
//        signUpBox.toFront();
        signInBox.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        signUpBox.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        signUpMid.setVisible(false);
        setAlignment(signInBox, Pos.CENTER_RIGHT);
        setAlignment(signUpBox, Pos.CENTER_RIGHT);
        setAlignment(signUpMid, Pos.CENTER_RIGHT);
        setMargin(signInBox, new Insets(50));
        setMargin(signUpBox, new Insets(50));
        setMargin(signUpMid, new Insets(50));
//        setBackground(new Background(new BackgroundFill(Color.TAN, null, null)));
        signUpMid.getStyleClass().add("sign-up-mid");
        setEvent();
    }

    // 设置相关事件
    private void setEvent() {
        //动画
        signUpBox.setRotate(rotateAngle);
        signInBox.toSignUpClicked(event -> play(signInBox, signUpBox));
        signUpBox.toSignInClicked(event -> play(signUpBox, signInBox));
        // 注册逻辑
        signUpBox.setSignUpClick();
        // 登录逻辑
        signInBox.setSignInClick();
    }

    // 启动动画
    private void play(Pane hide, Pane show) {
        signInBox.setDisable(true);
        signUpBox.setDisable(true);
        signInBox.setStyle("-fx-opacity: 1;");
        signUpBox.setStyle("-fx-opacity: 1;");
        TT1.setNode(hide);
        TT1.setFromX(0);
        TT1.setToX(translateX);
        RT.setNode(hide);
        RT.setFromAngle(0);
        RT.setToAngle(rotateAngle);
        hide.rotateProperty()
                // 监听旋转角度恢复要显示的内容
                .addListener((observable, oldValue, newValue) -> show.setRotate(rotateAngle - newValue.doubleValue()));
        TT2.setNode(hide);
        TT2.setFromX(translateX);
        TT2.setToX(0);
        PT.setOnFinished(event -> show.toFront()); // 动画完显示在前边
        SEQ_T.setInterpolator(Interpolator.EASE_BOTH);
        SEQ_T.setOnFinished(event -> {
            signInBox.setDisable(false);
            signUpBox.setDisable(false);
        });
        SEQ_T.play();
    }

    private void bindWidthHeight(Region... node) {
        for (Region region : node) {
            region.prefWidthProperty().bind(DEFAULT_WIDTH);
            region.prefHeightProperty().bind(DEFAULT_HEIGHT);
        }
    }

    public class SignInBox extends StackPane {

        private Label titleLabel = SimpleControl.getLabel("Login", SimpleControl.LabelEnum.H1);
        private StackPane titlePane = new StackPane(titleLabel);
        private WJTextField email = new WJTextField(WJTextField.Type.TEXT, new FontIcon(AntDesignIconsOutlined.MAIL));
        private WJTextField password = new WJTextField(WJTextField.Type.PASSWORD, new FontIcon(AntDesignIconsOutlined.KEY));
        private SimpleButton signIn = new SimpleButton("Login");
        private WJCheckBox checkBox = new WJCheckBox("Remember me");
        private StackPane checkPane = new StackPane(checkBox);
        private Hyperlink toSignUp = SimpleControl.getHyperlink("Don't have an account? Register", WJLevel.PRIMARY);
        //
        private VBox vBox = new VBox(titlePane, email, password, checkPane, signIn, toSignUp);

        public SignInBox() {
            initialize();
        }

        public void toSignUpClicked(Consumer<MouseEvent> consumer) {
            this.toSignUp.setOnMouseClicked(consumer::accept);
        }

        private void initialize() {
            //布局
            getChildren().add(vBox);
            StackPane.setAlignment(titleLabel, Pos.BOTTOM_LEFT);
            bindWidthHeight(email, password, signIn);
            signIn.prefWidthProperty().bind(password.widthProperty());
            //属性
            email.setPromptText("Email");
            password.setPromptText("Password");
            //styleClass
            vBox.getStyleClass().add("v-box-in");
            checkPane.getStyleClass().add("check-pane");

            email.setText("12345@gmail.com");
            password.setText("Anxious123");
        }

        private void setSignInClick() {
            signIn.setOnMouseClicked(event -> {
                event.consume();
                String emailText = email.getText();
                String passwordText = password.getText();
                if (Validation.Email.check(emailText)) return;
                UserFilterService.setUser(emailText);
                if (Validation.Password.check(passwordText)) return;
                WJMessage.show("SignIn successfully", WJLevel.SUCCESS);
                PauseTransition pause = new PauseTransition(Duration.millis(600));
                pause.setOnFinished(e -> wjStage.toMainPage());
                pause.play();
            });
        }
    }

    public class SignUpBox extends StackPane {

        private Label titleLabel = SimpleControl.getLabel("Registration", SimpleControl.LabelEnum.H1);
        private StackPane titlePane = new StackPane(titleLabel);
        private WJTextField user = new WJTextField(WJTextField.Type.TEXT, new FontIcon(AntDesignIconsOutlined.USER));
        private WJTextField email = new WJTextField(WJTextField.Type.TEXT, new FontIcon(AntDesignIconsOutlined.MAIL));
        private WJTextField password = new WJTextField(WJTextField.Type.PASSWORD, new FontIcon(AntDesignIconsOutlined.KEY));
        private SimpleButton signUp = new SimpleButton("Registration");
        private Hyperlink toSignIn = SimpleControl.getHyperlink("Already have an account? Login", WJLevel.PRIMARY);
        //
        private VBox vBox = new VBox(titlePane, user, email, password, signUp, toSignIn);

        public SignUpBox() {
            initialize();
        }

        private void initialize() {
            //布局
            getChildren().add(vBox);
            bindWidthHeight(user, email, password, signUp);
            StackPane.setAlignment(titleLabel, Pos.CENTER_LEFT);
            signUp.prefWidthProperty().bind(password.widthProperty());
            //属性
            user.setPromptText("Username");
            email.setPromptText("Email");
            password.setPromptText("Password");
            //styleClass
            vBox.getStyleClass().add("v-box-up");

            user.setText("Bob");
            email.setText("12345@gmail.com");
            password.setText("Anxious123");
        }

        private void setSignUpClick() {
            signUp.setOnMouseClicked(event -> {
                String userText = user.getText();
                String emailText = email.getText();
                String passwordText = password.getText();
                if (Validation.Username.check(userText)) return;
                if (Validation.Email.check(emailText, true)) return;
                if (Validation.Password.check(passwordText, true)) return;
                System.out.println(passwordText);
                WJMessage.show("Registration successfully", WJLevel.SUCCESS);
                WJMid wjMid = new WJMid(wjStage, userText, emailText, passwordText);
                AnimationUtils.silkyPopupAnimation(wjStage, WJLogin.this, wjMid, 90, Duration.seconds(0.3)).play();
            });
        }

        public void toSignInClicked(Consumer<MouseEvent> consumer) {
            this.toSignIn.setOnMouseClicked(consumer::accept);
        }
    }

    @Override
    public String getUserAgentStylesheet() {
        return ResourcesLoader.load("/css/Login.css");
    }
}
