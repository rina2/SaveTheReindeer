package com.caucse.rina.savethereindeer;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog {
    private Context context;
    private Stage stage;
    public static final int DIALOG_GAME_START = 24;
    public static final int DIALOG_GAME_WIN = 25;
    public static final int DIALOG_GAME_LOSE = 26;
    public static final int DIALOG_GAME_EXIT = 27;

    public CustomDialog(Context context, final Stage stage) {
        this.context = context;
        this.stage = stage;
    }

    public void CallFunction(int MODE) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        switch (MODE) {
            case DIALOG_GAME_START:
                dialog.setContentView(R.layout.custom_start_game);
                setGameStartDialog(dialog);
                break;
            case DIALOG_GAME_WIN:
                dialog.setContentView(R.layout.custom_win_game);
                setGameWinDialog(dialog);
                break;
            case DIALOG_GAME_LOSE:
                dialog.setContentView(R.layout.custom_lose_game);
                setGameLoseDialog(dialog);
                break;
            case DIALOG_GAME_EXIT:
                dialog.setContentView(R.layout.custom_exit_game);
                setGameExitDialog(dialog);
                break;
        }
        dialog.show();

    }


    private void setGameStartDialog(final Dialog dialog) {
        final Button btnStart = dialog.findViewById(R.id.btnGameStart);
        final TextView tvStageNumber = dialog.findViewById(R.id.tvStageNumber);
        tvStageNumber.setText("STAGE " + stage.getStageNumber());
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void setGameWinDialog(final Dialog dialog) {
        final Button btnToListActivity = dialog.findViewById(R.id.btnToListActivity);
        final Button btnNextStage = dialog.findViewById(R.id.btnNextStage);


        btnToListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dialog.getContext(), StageListActivity.class);
                context.startActivity(intent);
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });

        btnNextStage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(dialog.getContext(), PlayActivity.class);
                intent.putExtra("stageNumber", stage.getStageNumber() + 1);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }


    private void setGameLoseDialog(final Dialog dialog) {
        final Button btnTryAgain = dialog.findViewById(R.id.btnTryAgain);
        final Button btnListMenu = dialog.findViewById(R.id.btnToListActivity);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(dialog.getContext(), PlayActivity.class);
                intent.putExtra("stageNumber", stage.getStageNumber());
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });

        btnListMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dialog.getContext(), StageListActivity.class);
                context.startActivity(intent);
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });
    }

    private void setGameExitDialog(final Dialog dialog) {
        Button btnExit = dialog.findViewById(R.id.btnExit);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), StageListActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();

            }
        });
    }
}
