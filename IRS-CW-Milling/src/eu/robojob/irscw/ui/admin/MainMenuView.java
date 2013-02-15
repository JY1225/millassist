package eu.robojob.irscw.ui.admin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import eu.robojob.irscw.ui.general.AbstractMenuView;

public class MainMenuView extends AbstractMenuView<MainMenuPresenter> {

	private static final String GENERAL_PATH = "M 2.5 0.0625 C 1.81125 0.0625 1.25 0.621875 1.25 1.3125 L 1.25 7.5625 L 0 7.5625 L 0 10.0625 L 1.25 10.0625 L 1.25 18.8125 C 1.25 19.503125 1.81125 20.0625 2.5 20.0625 C 3.193125 20.0625 3.75 19.503125 3.75 18.8125 L 3.75 10.0625 L 5 10.0625 L 5 7.5625 L 3.75 7.5625 L 3.75 1.3125 C 3.75 0.621875 3.193125 0.0625 2.5 0.0625 z M 10 0.0625 C 9.3112496 0.0625 8.75 0.621875 8.75 1.3125 L 8.75 12.5625 L 7.5 12.5625 L 7.5 15.0625 L 8.75 15.0625 L 8.75 18.8125 C 8.75 19.503125 9.3112496 20.0625 10 20.0625 C 10.693125 20.0625 11.25 19.503125 11.25 18.8125 L 11.25 15.0625 L 12.5 15.0625 L 12.5 12.5625 L 11.25 12.5625 L 11.25 1.3125 C 11.25 0.621875 10.693125 0.0625 10 0.0625 z M 17.5 0.0625 C 16.81125 0.0625 16.25 0.621875 16.25 1.3125 L 16.25 5.0625 L 15 5.0625 L 15 7.5625 L 16.25 7.5625 L 16.25 18.8125 C 16.25 19.503125 16.81125 20.0625 17.5 20.0625 C 18.193125 20.0625 18.75 19.503125 18.75 18.8125 L 18.75 7.5625 L 20 7.5625 L 20 5.0625 L 18.75 5.0625 L 18.75 1.3125 C 18.75 0.621875 18.193125 0.0625 17.5 0.0625 z";
	private static final String ROBOT_PATH = "M 14.5625 0 C 14.505123 0.0096481784 14.456184 0.01730653 14.40625 0.0625 C 14.294601 0.16354353 14.203088 0.18980634 14.125 0.125 C 14.054825 0.066754317 13.992827 0.075342893 13.875 0.125 C 13.782618 0.16393397 13.543245 0.23875075 13.375 0.28125 C 13.206756 0.3237484 13.084581 0.37995901 13.0625 0.4375 C 13.033387 0.51335197 12.948764 0.53142462 12.78125 0.5 C 12.456348 0.43904815 11.856324 0.70271315 11.71875 0.96875 C 11.661334 1.0797753 11.618383 1.3515059 11.59375 1.53125 C 11.555973 1.8068429 11.497052 1.863418 11.28125 2 C 11.049926 2.1464017 11.012978 2.1657207 10.9375 2.0625 C 10.86269 1.9601878 10.826227 1.9657082 10.65625 2.125 C 10.551918 2.222782 10.408792 2.3888928 10.34375 2.5 C 10.223017 2.7062342 9.387252 3.25 9.1875 3.25 C 9.1267853 3.25 9.0030452 3.368714 8.9375 3.46875 C 8.8126803 3.6592548 8.385463 3.90625 8.1875 3.90625 C 8.123935 3.90625 7.9362798 3.9831012 7.8125 4.125 C 7.5709978 4.4018547 7.2514564 4.59375 6.96875 4.59375 C 6.8559271 4.59375 6.7790571 4.6272011 6.75 4.71875 C 6.6955508 4.8903031 6.5326703 4.9042139 6.3125 4.75 C 6.1318319 4.6234552 5.688424 4.7136069 5.5 4.875 C 5.3868896 4.9718845 5.1245081 5.0326429 4.6875 5.09375 C 4.4696819 5.124208 4.4072038 5.1754254 4.3125 5.375 C 4.2486756 5.5094995 4.1023796 5.6345583 4 5.6875 C 3.8976204 5.7404434 3.7656341 5.8507816 3.6875 5.9375 C 3.6093659 6.0242193 3.4332275 6.1220339 3.34375 6.15625 L 3.1875 6.21875 L 3.34375 6.4375 C 3.4283868 6.5519762 3.5526536 6.6685816 3.625 6.6875 C 3.6973456 6.7064193 3.8267147 6.8002736 3.875 6.90625 C 3.9671478 7.1084923 3.9369475 7.1331213 3.59375 7.34375 C 3.491431 7.4065463 3.5349637 7.4250151 3.71875 7.625 L 3.9375 7.84375 L 3.78125 8 C 3.5587353 8.209041 3.3903215 8.6371202 3.46875 8.96875 C 3.5130613 9.156117 3.6267708 9.3123882 3.875 9.5 C 4.0622322 9.6415097 4.3001061 9.876849 4.375 10 C 4.4783359 10.169922 4.5592398 10.21875 4.71875 10.21875 C 5.0053544 10.21875 5.074522 10.083654 4.8125 10.03125 C 4.572623 9.9832744 4.4581721 9.7479124 4.59375 9.5625 C 4.6776915 9.447703 4.7219299 9.4796033 5.03125 9.625 C 5.2125827 9.7102365 5.375 9.8504161 5.375 9.90625 C 5.375 9.9620821 5.4312018 10.009414 5.5 10.03125 C 5.5687982 10.053086 5.6487367 10.131515 5.6875 10.21875 C 5.7282665 10.310493 5.866714 10.412911 6.03125 10.46875 C 6.6085402 10.664664 6.7597548 10.796235 6.84375 11.0625 C 6.8882823 11.20367 7.0007806 11.424382 7.09375 11.5625 C 7.289918 11.853931 7.3875398 12.336671 7.40625 13.03125 C 7.4150561 13.357952 7.5206884 13.696777 7.65625 14.09375 C 7.7685797 14.422693 7.875 14.832874 7.875 14.96875 C 7.875 15.104627 7.8906796 15.34373 7.9375 15.5 C 8.0200278 15.775453 8.002752 15.802088 7.78125 16 C 7.5923218 16.168807 7.4622 16.17576 7.125 16.1875 C 6.4613488 16.210605 6.3510621 16.182715 6.03125 16.03125 C 4.9963048 15.540892 4.6780732 15.563727 4.25 16 C 4.0851901 16.167965 3.9293025 16.45516 3.875 16.625 C 3.7708306 16.950809 3.7293041 16.90548 4.09375 17.9375 C 4.1593583 18.123287 4.2584663 18.25 4.3125 18.25 C 4.4382804 18.25 4.4375373 18.39768 4.3125 18.5 C 4.2594346 18.543425 4.1548076 18.684766 4.09375 18.84375 C 3.9930767 19.105858 3.9927551 19.166468 4.09375 19.375 C 4.1584804 19.508651 4.3358297 19.631963 4.46875 19.6875 C 4.6181052 19.74991 4.6872426 19.843213 4.71875 19.96875 C 4.8019808 20.300369 4.7163905 20.393833 3.6875 20.53125 C 3.2681314 20.587254 3.198713 20.687948 3.5 20.84375 C 3.7100793 20.952383 3.7390189 20.961843 3.71875 21.3125 C 3.7005098 21.628081 3.6300657 21.731456 3.5 21.75 C 3.3958382 21.764851 3.3582116 21.683219 3.25 21.53125 L 3.0625 21.3125 L 2.90625 21.5 C 2.8338575 21.619042 2.7486563 21.758058 2.65625 21.78125 C 2.4944235 21.821864 2.4558027 21.881028 2.375 22.875 C 2.3288578 23.442581 2.2936515 23.938251 2.3125 23.96875 C 2.3446421 24.020763 2.9266872 24.124071 3.40625 24.1875 C 3.7657016 24.235046 5.1754539 24.611466 5.40625 24.71875 C 5.5259347 24.774382 5.7755305 24.853233 5.96875 24.90625 C 6.5187644 25.057162 6.9183199 25.004517 7.25 24.8125 C 7.409889 24.719938 7.7223778 24.645355 7.9375 24.59375 C 8.1526214 24.542149 8.3445005 24.426398 8.40625 24.375 C 8.4680047 24.323604 8.6449279 24.28125 8.75 24.28125 C 8.8550772 24.28125 9.1194395 24.243709 9.34375 24.1875 C 9.5680588 24.13129 9.8981835 24.063049 10.09375 24.03125 C 10.427537 23.976979 10.40625 23.979287 10.40625 23.78125 C 10.40625 23.591365 10.387943 23.551843 10.09375 23.46875 C 9.7627575 23.375264 9.59375 23.176209 9.59375 22.875 C 9.59375 22.801872 9.5735198 22.73284 9.53125 22.71875 C 9.4889794 22.704656 9.4642767 22.599653 9.4375 22.46875 C 9.3983493 22.27731 9.3243331 22.189428 9.125 22.125 C 8.9445179 22.06666 8.8616975 22.033445 8.84375 21.90625 C 8.8039066 21.623843 8.9931521 21.201009 9.21875 21 C 9.33407 20.897245 9.4375 20.740279 9.4375 20.6875 C 9.4375 20.563759 9.7611999 20.349248 10.125 20.25 C 10.469749 20.155949 10.539861 20.034505 10.40625 19.84375 C 10.247507 19.617114 10.294642 19.560938 10.65625 19.375 C 10.962801 19.217374 11.040885 19.140649 11.34375 18.53125 C 11.842289 17.528136 11.877269 17.434325 11.4375 16.5625 C 11.175748 16.043585 11.047708 15.711879 11 15.375 C 10.963173 15.114993 10.882002 14.759526 10.84375 14.59375 C 10.805499 14.427977 10.782339 14.176008 10.78125 14 C 10.780064 13.808032 10.663604 13.444947 10.53125 13.125 C 10.409897 12.831652 10.221807 12.277825 10.09375 11.90625 C 9.9656926 11.534674 9.6843878 10.897489 9.5 10.46875 L 9.1875 9.71875 L 9.28125 9.28125 C 9.3728493 8.9787149 9.3964497 8.6903619 9.375 8.15625 C 9.3386476 7.2512007 9.1551613 6.792492 8.5625 6.3125 C 8.3474819 6.1383584 8.1231458 5.9559182 8.09375 5.90625 C 7.9909891 5.7326331 8.1605229 5.5849556 8.78125 5.1875 C 9.1252469 4.9672376 9.464575 4.680134 9.59375 4.59375 C 9.722925 4.5073651 10.068833 4.3714166 10.3125 4.25 C 10.556167 4.1285834 10.758595 4.0054052 10.78125 3.96875 C 10.803906 3.9320939 11.012132 3.90625 11.21875 3.90625 C 11.514792 3.90625 11.627309 3.8448426 11.75 3.71875 C 12.015359 3.4460322 12.317876 3.0794477 12.40625 2.875 C 12.462806 2.7441665 12.516934 2.6877849 12.625 2.6875 C 12.713839 2.6872441 12.882661 2.608961 13 2.53125 C 13.153453 2.4296221 13.316163 2.4066058 13.625 2.40625 C 13.997535 2.4058234 14.098877 2.3388453 14.3125 2.15625 C 14.474488 2.0177902 14.626604 1.9706491 14.78125 1.96875 C 14.99663 1.9661053 15.051907 1.9269331 15.21875 1.5625 C 15.437882 1.0838517 15.389434 0.76445545 15.09375 0.34375 C 14.902738 0.071973212 14.734614 -0.028948801 14.5625 0 z M 7.71875 6.15625 C 8.2728825 6.1471231 8.5429311 6.370179 8.90625 7.0625 C 9.1123374 7.4552122 9.174997 8.2613532 9.0625 8.6875 C 9.0270596 8.821735 8.980001 8.9545844 8.9375 8.96875 C 8.8603453 8.9944688 8.4193776 8.4925806 8.25 8.15625 C 8.2007558 8.0584672 8.1208623 7.8790328 8.03125 7.78125 C 7.9416377 7.6834672 7.8271456 7.4572582 7.78125 7.28125 C 7.7353552 7.1052409 7.6286711 6.8530851 7.53125 6.75 C 7.4082261 6.6198242 7.3588253 6.5789799 7.375 6.4375 C 7.3970631 6.2444997 7.3818589 6.1617971 7.71875 6.15625 z";
	private static final String DEVICE_PATH = "M 10.3125,0.03125 C 10.090768,0.52621332 9.8777772,1.0417871 9.71875,1.5625 9.4096363,1.5775206 9.118395,1.5682556 8.8125,1.625 8.5046977,1.6662469 8.2053499,1.7261914 7.90625,1.8125 7.6095343,1.3671284 7.2594717,0.91151575 6.90625,0.5 6.1880048,0.74497824 5.4870116,1.0676056 4.84375,1.46875 4.9306547,2.0153316 5.0390825,2.5073184 5.1875,3.03125 4.9425218,3.2204376 4.6935816,3.4116126 4.46875,3.625 4.239746,3.8334997 3.9828355,4.0138434 3.78125,4.25 3.2901014,4.0348245 2.7766734,3.8501036 2.25,3.6875 1.7822167,4.2816467 1.3582453,4.9080494 1.03125,5.59375 1.3981809,6.0070539 1.7870709,6.3917715 2.1875,6.75 2.0828329,7.0418281 1.9567786,7.3257808 1.875,7.625 1.8035927,7.9267226 1.6929669,8.2227324 1.65625,8.53125 1.1273116,8.616843 0.59727974,8.7572365 0.0625,8.90625 c -0.0821363,0.7521011 -0.0822555,1.497899 0,2.25 0.53489895,0.148894 1.0648116,0.258157 1.59375,0.34375 0.036717,0.308398 0.1473427,0.604527 0.21875,0.90625 0.082017,0.299219 0.2078329,0.583053 0.3125,0.875 -0.4004291,0.358348 -0.7893191,0.742946 -1.15625,1.15625 0.3269953,0.685581 0.7509667,1.312103 1.21875,1.90625 0.5266734,-0.162604 1.0399822,-0.347205 1.53125,-0.5625 0.2013471,0.236037 0.458496,0.447869 0.6875,0.65625 0.2248316,0.213507 0.473891,0.373312 0.71875,0.5625 -0.1484175,0.524051 -0.2567261,1.047168 -0.34375,1.59375 0.6433808,0.401144 1.3441356,0.692522 2.0625,0.9375 0.3532217,-0.411635 0.7032843,-0.867128 1,-1.3125 0.2990999,0.08631 0.5985669,0.146491 0.90625,0.1875 0.3060142,0.05686 0.5971363,0.07885 0.90625,0.09375 0.1591464,0.520832 0.371899,1.036406 0.59375,1.53125 0.752578,-0.02539 1.486676,-0.139899 2.21875,-0.34375 0.072,-0.548727 0.120351,-1.075319 0.125,-1.625 0.289325,-0.111701 0.599742,-0.168016 0.875,-0.3125 0.280384,-0.133516 0.552263,-0.268101 0.8125,-0.4375 0.411039,0.344042 0.854015,0.667623 1.3125,0.96875 0.61632,-0.438696 1.214488,-0.935537 1.71875,-1.5 -0.236753,-0.499732 -0.49419,-0.946811 -0.78125,-1.40625 0.182035,-0.250224 0.373057,-0.514456 0.53125,-0.78125 0.159385,-0.26584 0.342863,-0.496694 0.46875,-0.78125 0.532753,0.06819 1.06912,0.08481 1.625,0.09375 0.281576,-0.704774 0.534774,-1.43826 0.65625,-2.1875 C 19.405786,11.426445 18.918635,11.166981 18.4375,10.9375 18.46671,10.629459 18.48522,10.340006 18.5,10.03125 18.485218,9.722494 18.466587,9.4016715 18.4375,9.09375 18.918635,8.86415 19.405786,8.6048049 19.875,8.3125 19.753524,7.56326 19.500445,6.8297744 19.21875,6.125 18.66287,6.1338216 18.126503,6.181573 17.59375,6.25 17.467863,5.9654438 17.284385,5.7033401 17.125,5.4375 16.966688,5.1707062 16.776023,4.9377235 16.59375,4.6875 16.88081,4.2280615 17.138247,3.7497318 17.375,3.25 16.870619,2.6855368 16.27257,2.2198266 15.65625,1.78125 15.197646,2.0824957 14.75467,2.4058384 14.34375,2.75 14.083394,2.5804822 13.811395,2.4460161 13.53125,2.3125 13.255873,2.1680165 12.945694,2.0803313 12.65625,1.96875 12.65165,1.4190689 12.60325,0.8924774 12.53125,0.34375 11.799057,0.13966108 11.065197,0.0566419 10.3125,0.03125 z m -0.40625,6.5 c 1.932997,0 3.5,1.567003 3.5,3.5 0,1.932997 -1.567003,3.5 -3.5,3.5 -0.6593574,0 -1.2859787,-0.183014 -1.8125,-0.5 C 8.0753954,13.019413 8.049401,13.012173 8.03125,13 7.9547977,12.951855 7.8848134,12.897496 7.8125,12.84375 7.7624,12.804835 7.7043834,12.760282 7.65625,12.71875 7.5735231,12.647554 7.5129398,12.57843 7.4375,12.5 7.3954618,12.457962 7.3523655,12.419123 7.3125,12.375 7.2688856,12.324586 7.2281751,12.271453 7.1875,12.21875 7.1207916,12.135796 7.0590481,12.057664 7,11.96875 6.9080065,11.825555 6.8207721,11.653996 6.75,11.5 6.6792279,11.346004 6.6418218,11.19281 6.59375,11.03125 6.4504288,10.546837 6.3970726,10.006961 6.46875,9.5 c 0.023892,-0.1689869 0.077197,-0.3072491 0.125,-0.46875 0.048042,-0.1615307 0.085495,-0.3460206 0.15625,-0.5 0.0098,-0.02132 0.021034,-0.04136 0.03125,-0.0625 C 6.8463389,8.3377627 6.9196157,8.2147914 7,8.09375 7.0044454,8.0868302 6.9955068,8.0693906 7,8.0625 7.0545483,7.9817579 7.1265816,7.9195038 7.1875,7.84375 7.2643495,7.7441743 7.3502951,7.6535994 7.4375,7.5625 7.5853343,7.4146657 7.7339978,7.2758485 7.90625,7.15625 7.9377056,7.1344097 7.9678054,7.1145734 8,7.09375 c 0.030384,-0.020922 0.062781,-0.04253 0.09375,-0.0625 0.5265213,-0.3169858 1.1531426,-0.5 1.8125,-0.5 z";
	
	public MainMenuView() {
		build();
	}

	@Override
	protected void build() {
		int index = 0;
		addIconMenuItem(index++, GENERAL_PATH, new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				getPresenter().generalMenuClicked();
			}
		});
		addIconMenuItem(index++, ROBOT_PATH, new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				getPresenter().robotMenuClicked();
			}
		});
		addIconMenuItem(index++, DEVICE_PATH, new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				getPresenter().deviceMenuClicked();
			}
		});
	}

	public void setGeneralMenuActive() {
		setMenuItemSelected(0);
	}
	
	public void setRobotMenuActive() {
		setMenuItemSelected(1);
	}
	
	public void setDeviceMenuActive() {
		setMenuItemSelected(2);
	}
}
