package com.gxg.demo8.mydemo8.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.gxg.demo8.mydemo8.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Administrator on 2018/3/2 16:39
 * 邮箱：android_gaoxuge@163.com
 */
public class LottieActivity extends AppCompatActivity {
    @Bind(R.id.animation_view_click)
    LottieAnimationView animation_view_click;
    @Bind(R.id.tv_seek)
    TextView tvSeek;
    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        ButterKnife.bind(this);

        setdata(type);


        animation_view_click.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animation_view_click.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tvSeek.setText(" 动画进度" +(int) (animation.getAnimatedFraction()*100) +"%");
            }
        });
    }

    private String[] titles = {"Logo/LogoSmall.json","lottiefiles/100_percent.json","lottiefiles/___.json","lottiefiles/accept_arrows.json",
                                "lottiefiles/android_fingerprint.json","AndroidWave.json","9squares-AlBoardman.json","lottiefiles/animated_laptop_.json",
            "lottiefiles/animated_logo.json","lottiefiles/atm_link.json","lottiefiles/autoconnect_loading.json","lottiefiles/bell.json",
            "lottiefiles/birds.json","lottiefiles/bootymovin.json","lottiefiles/bounching_ball.json","lottiefiles/brain__.json",
            "lottiefiles/camera.json","lottiefiles/cancel_button.json","lottiefiles/check_pop.json","lottiefiles/chinese.json",
            "lottiefiles/clock.json","lottiefiles/cloud_disconnection.json","lottiefiles/code_invite_success.json","lottiefiles/coding_ape.json",
            "lottiefiles/colorline.json","lottiefiles/confusion.json","lottiefiles/construction_site.json","lottiefiles/cooking_app.json",
            "lottiefiles/countdown.json","lottiefiles/cube_loader.json","lottiefiles/curly_hair_character_loop.json","lottiefiles/cycle_animation.json",
            "lottiefiles/day_night_cycle.json","lottiefiles/done.json","lottiefiles/download.json","lottiefiles/drop_to_refresh.json",
            "lottiefiles/elephant_trunk_swing.json","lottiefiles/emoji_shock.json","lottiefiles/emoji_tongue.json","lottiefiles/emoji_wink.json",
            "lottiefiles/empty_status.json","lottiefiles/estimate.json","lottiefiles/fab_animate.json","lottiefiles/favourite_app_icon.json",
            "lottiefiles/finish,done.json","lottiefiles/fish.json","lottiefiles/flow.json","lottiefiles/gears.json",
            "lottiefiles/glow_loading.json","lottiefiles/gradient_animated_background.json", "lottiefiles/happy_birthday.json","lottiefiles/hardware.json",
            "lottiefiles/hint_01.json","lottiefiles/i'm_thirsty!.json","lottiefiles/india.json","lottiefiles/infinite_rainbow.json",
            "lottiefiles/iphone_x_loading.json","lottiefiles/lego_loader.json","lottiefiles/lightsaber.json","lottiefiles/loading disc.json",
            "lottiefiles/loading.json","lottiefiles/loading_semicircle.json","lottiefiles/location.json","lottiefiles/location_marker.json",
            "lottiefiles/location_pin.json","lottiefiles/lottie_logo_1.json","lottiefiles/lottiepreview_qr.json","lottiefiles/mailsent.json",
            "lottiefiles/map_animation.json","lottiefiles/material loading.json","lottiefiles/material_loader.json","lottiefiles/material_loading_2.json",
            "lottiefiles/material_wave_loading.json","lottiefiles/medal.json","lottiefiles/menu_button_alt.json","lottiefiles/menuButton2.json",
            "lottiefiles/mindful.json","lottiefiles/mnemonics.json","lottiefiles/money.json","lottiefiles/motorcycle.json",
            "lottiefiles/moving_bus.json","lottiefiles/moving_eye.json","lottiefiles/notification_request.json","lottiefiles/octopus.json",
            "lottiefiles/on_off_settings_switch.json","lottiefiles/pagado.json","lottiefiles/pagination.json","lottiefiles/panel2d.json",
            "lottiefiles/payme.json","lottiefiles/pen_tool_loop.json","lottiefiles/pencil_write.json","lottiefiles/penguin.json",
            "lottiefiles/permission.json","lottiefiles/phonological.json","lottiefiles/pink_drink_machine.json","lottiefiles/Plane.json",
            "lottiefiles/plane_to_dollar.json","lottiefiles/play,_pause.json","lottiefiles/play_and_like_it.json","lottiefiles/play_button.json",
            "lottiefiles/playing.json","lottiefiles/poo_loader.json","lottiefiles/preloader.json","lottiefiles/progress_bar 2.json",
            "lottiefiles/progress_bar.json","lottiefiles/rating.json","lottiefiles/rejection.json","lottiefiles/retweet.json",
            "lottiefiles/rocksauce_title_card.json","lottiefiles/scan_qr_code_success.json","lottiefiles/search_button.json","lottiefiles/servishero_loading.json",
            "lottiefiles/simple_loader.json","lottiefiles/socar_logo.json","lottiefiles/soda_loader.json","lottiefiles/spacehub.json",
            "lottiefiles/spinner loading.json","lottiefiles/splashy_loader.json","lottiefiles/square_drop_loader.json","lottiefiles/star 2.json",
            "lottiefiles/streetby_test_loading.json","lottiefiles/StreetByMorning.json","lottiefiles/submit button.json","lottiefiles/swiftkey-logo.json",
            "lottiefiles/swipe_right_indicator.json","lottiefiles/switch_loop.json","lottiefiles/the_final_frontier.json", "lottiefiles/timer_3_second_loop.json",
            "lottiefiles/toggle.json","lottiefiles/toggle_switch.json","lottiefiles/touch_id.json","lottiefiles/trail_loading.json",
            "lottiefiles/trophy.json","lottiefiles/turbine.json","lottiefiles/typing dot.json","lottiefiles/uk.json",
            "lottiefiles/updating_map.json","lottiefiles/us.json","lottiefiles/video_cam.json","lottiefiles/volume_indicator.json",
            "lottiefiles/volume_shake_indicator.json","lottiefiles/vr_animation.json","lottiefiles/vr_sickness.json","lottiefiles/wallet recharge.json",
            "lottiefiles/x_pop.json","lottiefiles/xamarin_logo_2.json","lottiefiles/xuanwheel_logo.json","lottiefiles/youtube_icon_reveal.json",
    };

    private void setdata(int type) {
        LottieComposition.Factory.fromAssetFileName(this, titles[type], new OnCompositionLoadedListener() {

            @Override
            public void onCompositionLoaded(LottieComposition composition) {
                animation_view_click.setComposition(composition);
                animation_view_click.setProgress(0.333f);

                animation_view_click.playAnimation();
            }
        });

    }

    @OnClick({R.id.button3,R.id.tv_seek, R.id.button1, R.id.button2, R.id.activity_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_seek:
                break;
            case R.id.button1:
                startAnima();
                break;
            case R.id.button2:
                stopAnima();
                break;
            case R.id.activity_main:
                break;
            case R.id.button3:
                type++;
                if(type==titles.length){
                    type=0;
                }
                setdata(type);
                break;
        }
    }

    /*
   * 开始动画
   */
    private  void startAnima(){

        boolean inPlaying = animation_view_click.isAnimating();
        if (!inPlaying) {
            animation_view_click.setProgress(0f);
            animation_view_click.playAnimation();
        }
    }
    /*
    * 停止动画
    */
    private  void stopAnima(){
        boolean inPlaying = animation_view_click.isAnimating();
        if (inPlaying) {
            animation_view_click.cancelAnimation();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        animation_view_click.cancelAnimation();
    }

}
