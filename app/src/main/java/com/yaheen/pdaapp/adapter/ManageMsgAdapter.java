package com.yaheen.pdaapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yaheen.pdaapp.R;
import com.yaheen.pdaapp.adapter.base.CommonAdapter;
import com.yaheen.pdaapp.bean.MsgBean;

public class ManageMsgAdapter extends CommonAdapter<MsgBean.EntityBean> {

    private boolean change = false;

    private boolean isScrolling = false;

    private boolean canChange = false;

    private int touchPosition = -1;

    private AlertDialog dialog;

    public ManageMsgAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return MsgBean.num;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_manage_msg, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MsgBean.EntityBean data = getItem(0);
        if (data != null) {
            if (position == 0) {
                holder.tvDescribe.setText(R.string.msg_community_text);
                holder.etDetail.setText(data.getCommunity());
            } else if (position == 1) {
                holder.tvDescribe.setText(R.string.msg_username_text);
                holder.etDetail.setText(data.getUserName());
            } else if (position == 2) {
                holder.tvDescribe.setText(R.string.msg_sex_text);
                holder.etDetail.setText(data.getSex());
            } else if (position == 3) {
                holder.tvDescribe.setText(R.string.msg_address_text);
                holder.etDetail.setText(data.getAddress());
            } else if (position == 4) {
                holder.tvDescribe.setText(R.string.msg_mobile_text);
                holder.etDetail.setText(data.getMobile());
            } else if (position == 5) {
                holder.tvDescribe.setText(R.string.msg_phone_text);
                holder.etDetail.setText(data.getTelephone());
            } else if (position == 6) {
                holder.tvDescribe.setText(R.string.msg_people_number_text);
                holder.etDetail.setText(data.getPeopleNumber());
            } else if (position == 7) {
                holder.tvDescribe.setText(R.string.msg_category_text);
                holder.etDetail.setText(data.getCategory());
            } else if (position == 8) {
                holder.tvDescribe.setText(R.string.msg_party_member_text);
                holder.etDetail.setText(data.getPartyMember());
            } else if (position == 9) {
                holder.tvDescribe.setText(R.string.msg_gloriousArmy_text);
                holder.etDetail.setText(data.getGloriousArmy());
            } else if (position == 10) {
                holder.tvDescribe.setText(R.string.msg_fiveGuarantees_text);
                holder.etDetail.setText(data.getFiveGuarantees());
            } else if (position == 11) {
                holder.tvDescribe.setText(R.string.msg_beekeepingProfessionals_text);
                holder.etDetail.setText(data.getBeekeepingProfessionals());
            } else if (position == 12) {
                holder.tvDescribe.setText(R.string.msg_fireInspectionPoint_text);
                holder.etDetail.setText(data.getFireInspectionPoint());
            } else if (position == 13) {
                holder.tvDescribe.setText(R.string.msg_gridInspectionPoint_text);
                holder.etDetail.setText(data.getGridInspectionPoint());
            } else if (position == 14) {
                holder.tvDescribe.setText(R.string.msg_breedingSpecialist_text);
                holder.etDetail.setText(data.getBreedingSpecialist());
            } else if (position == 15) {
                holder.tvDescribe.setText(R.string.msg_technologyDemonstration_text);
                holder.etDetail.setText(data.getTechnologyDemonstration());
            } else if (position == 16) {
                holder.tvDescribe.setText(R.string.msg_precisionPoverty_text);
                holder.etDetail.setText(data.getPrecisionPoverty());
            } else if (position == 17) {
                holder.tvDescribe.setText(R.string.msg_civilizationHouseholds_text);
                holder.etDetail.setText(data.getCivilizationHouseholds());
            } else if (position == 18) {
                holder.tvDescribe.setText(R.string.msg_helper_text);
                holder.etDetail.setText(data.getHelper());
            } else if (position == 19) {
                holder.tvDescribe.setText(R.string.msg_agriculturalProductsInformation_text);
                holder.etDetail.setText(data.getAgriculturalProductsInformation());
            }

            holder.etDetail.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if (isScrolling) {
                            return false;
                        }
                        if (position == 0) {
                            String str[] = {"行政村", "自然村"};
                            showCheckDialog(position, R.string.msg_community_text, str);
                            return true;
                        } else if (position == 2) {
                            String str[] = {"男", "女"};
                            showCheckDialog(position, R.string.msg_sex_text, str);
                            return true;
                        } else if (position == 7) {
                            String str[] = {"户主", "村委", "商旅", "导视牌"};
                            showCheckDialog(position, R.string.msg_category_text, str);
                            return true;
                        } else if (position == 8) {
                            String str[] = {"是", "否"};
                            showCheckDialog(position, R.string.msg_party_member_text, str);
                            return true;
                        } else if (position == 9) {
                            String str[] = {"是", "否"};
                            showCheckDialog(position, R.string.msg_gloriousArmy_text, str);
                            return true;
                        } else if (position == 10) {
                            String str[] = {"是", "否"};
                            showCheckDialog(position, R.string.msg_fiveGuarantees_text, str);
                            return true;
                        } else if (position == 11) {
                            String str[] = {"是", "否"};
                            showCheckDialog(position, R.string.msg_beekeepingProfessionals_text, str);
                            return true;
                        } else if (position == 14) {
                            String str[] = {"是", "否"};
                            showCheckDialog(position, R.string.msg_breedingSpecialist_text, str);
                            return true;
                        } else if (position == 15) {
                            String str[] = {"是", "否"};
                            showCheckDialog(position, R.string.msg_technologyDemonstration_text, str);
                            return true;
                        } else if (position == 16) {
                            String str[] = {"是", "否"};
                            showCheckDialog(position, R.string.msg_precisionPoverty_text, str);
                            return true;
                        } else if (position == 17) {
                            String str[] = {"是", "否"};
                            showCheckDialog(position, R.string.msg_civilizationHouseholds_text, str);
                            return true;
                        }
                    }

                    touchPosition = position;
                    return false;
                }
            });

            holder.etDetail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (isScrolling || !canChange) {
                        return;
                    }

                    if (touchPosition != position) {
                        return;
                    }

                    if (position == 1) {
                        data.setUserName(holder.etDetail.getText().toString());
                    } else if (position == 3) {
                        data.setAddress(holder.etDetail.getText().toString());
                    } else if (position == 4) {
                        data.setMobile(holder.etDetail.getText().toString());
                    } else if (position == 5) {
                        data.setTelephone(holder.etDetail.getText().toString());
                    } else if (position == 6) {
                        data.setPeopleNumber(holder.etDetail.getText().toString());
                    } else if (position == 12) {
                        data.setFireInspectionPoint(holder.etDetail.getText().toString());
                    } else if (position == 13) {
                        data.setGridInspectionPoint(holder.etDetail.getText().toString());
                    } else if (position == 18) {
                        data.setHelper(holder.etDetail.getText().toString());
                    } else if (position == 19) {
                        data.setAgriculturalProductsInformation(holder.etDetail.getText().toString());
                    }
                }
            });
        }
        return convertView;
    }

    private void showCheckDialog(final int position, int title, String str[]) {
        if (dialog != null && dialog.isShowing() || str == null || str.length == 0) {
            return;
        }

        canChange = false;

        final MsgBean.EntityBean data = getItem(0);

        dialog = new AlertDialog.Builder(context).setTitle(title)
                .setSingleChoiceItems(str, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (position == 0) {
                            if (which == 0) {
                                data.setCommunity("A");
                            } else if (which == 1) {
                                data.setCommunity("N");
                            }
                        } else if (position == 2) {
                            if (which == 0) {
                                data.setSex("M");
                            } else if (which == 1) {
                                data.setSex("F");
                            }
                        } else if (position == 7) {
                            if (which == 0) {
                                data.setCategory("S");
                            } else if (which == 1) {
                                data.setCategory("G");
                            } else if (which == 2) {
                                data.setCategory("B");
                            } else if (which == 3) {
                                data.setCategory("M");
                            }
                        } else if (position == 8) {
                            if (which == 0) {
                                data.setPartyMember("Y");
                            } else if (which == 1) {
                                data.setPartyMember("F");
                            }
                        } else if (position == 9) {
                            if (which == 0) {
                                data.setGloriousArmy("Y");
                            } else if (which == 1) {
                                data.setGloriousArmy("F");
                            }
                        } else if (position == 10) {
                            if (which == 0) {
                                data.setFiveGuarantees("Y");
                            } else if (which == 1) {
                                data.setFiveGuarantees("F");
                            }
                        } else if (position == 11) {
                            if (which == 0) {
                                data.setBeekeepingProfessionals("Y");
                            } else if (which == 1) {
                                data.setBeekeepingProfessionals("F");
                            }
                        } else if (position == 14) {
                            if (which == 0) {
                                data.setBreedingSpecialist("Y");
                            } else if (which == 1) {
                                data.setBreedingSpecialist("F");
                            }
                        } else if (position == 15) {
                            if (which == 0) {
                                data.setTechnologyDemonstration("Y");
                            } else if (which == 1) {
                                data.setTechnologyDemonstration("F");
                            }
                        } else if (position == 16) {
                            if (which == 0) {
                                data.setPrecisionPoverty("Y");
                            } else if (which == 1) {
                                data.setPrecisionPoverty("F");
                            }
                        } else if (position == 17) {
                            if (which == 0) {
                                data.setCivilizationHouseholds("Y");
                            } else if (which == 1) {
                                data.setCivilizationHouseholds("F");
                            }
                        }
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                }).create();
        dialog.show();
    }

    public boolean getChangeState() {
        return canChange;
    }

    public void setChangeState(boolean change) {
        this.canChange = change;
    }

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    public String getMsg() {
        MsgBean.EntityBean data = getItem(0);
        if (data != null) {
            Gson gson = new Gson();
            return gson.toJson(data);
        }
        return "";
    }

    /**
     * 判断是否读取了门牌数据
     */
    public boolean hasId() {
        MsgBean.EntityBean data = getItem(0);
        if (data != null) {
            if (!TextUtils.isEmpty(data.getId()) || !TextUtils.isEmpty(data.getUserId())
                    || !TextUtils.isEmpty(data.getHouseNumberId())) {
                return true;
            }
        }
        return false;
    }

    class ViewHolder {
        TextView tvDescribe;

        EditText etDetail;

        LinearLayout llItem;

        public ViewHolder(View view) {
            tvDescribe = view.findViewById(R.id.tv_describe);
            etDetail = view.findViewById(R.id.et_detail);
            llItem = view.findViewById(R.id.ll_item);
        }
    }
}
