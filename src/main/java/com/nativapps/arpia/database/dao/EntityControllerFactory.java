package com.nativapps.arpia.database.dao;

/**
 * The <strong>EntityControllerFactory</strong> class provided methods to get
 * entities controllers.
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0-SNAPSHOT
 */
public class EntityControllerFactory {

    public static PersonDao getPersonController() {
        return PersonDaoController.getInstance();
    }

    public static EmailDao getEmailController() {
        return EmailDaoController.getInstance();
    }

    public static AddressDao getAddressController() {
        return AddressDaoController.getInstance();
    }

    public static PhoneDao getPhoneController() {
        return PhoneDaoController.getInstance();
    }

    public static UserDao getUserController() {
        return UserDaoController.getInstance();
    }

    public static RoleDao getRoleController() {
        return RoleDaoController.getInstance();
    }

    public static PermissionDao getPermissionController() {
        return PermissionDaoController.getInstance();
    }

    public static ParticularDao getParticularController() {
        return ParticularDaoController.getInstance();
    }

    public static EstablishmentDao getEstablishmentController() {
        return EstablishmentDaoController.getInstance();
    }

    public static AdministratorDao getAdministratorController() {
        return AdministratorDaoController.getInstance();
    }

    public static MessengerDao getMessengerController() {
        return MessengerDaoController.getInstance();
    }

    public static InventoryDao getInventoryController() {
        return InventoryDaoController.getInstance();
    }

    public static DiseaseDao getDiseaseController() {
        return DiseaseDaoController.getInstance();
    }

    public static FaultDao getFaultController() {
        return FaultDaoController.getInstance();
    }

    public static ReferenceDao getReferenceController() {
        return ReferenceDaoController.getInstance();
    }

    public static OperationDao getOperationController() {
        return OperationDaoController.getInstance();
    }

    public static VehicleDao getVehicleController() {
        return VehicleDaoController.getInstance();
    }

    public static CustomerParamDao getCustomerParamController() {
        return CustomerParamDaoController.getInstance();
    }

    public static BonusDao getBonusController() {
        return BonusDaoController.getInstance();
    }

    public static CustomerDataDao getCustomerDataController() {
        return CustomerDataDaoController.getInstance();
    }

    public static ReliabilityDao getReliabilityController() {
        return ReliabilityDaoController.getInstance();
    }

    public static NeighborhoodDao getNeighborhoodController() {
        return NeighborhoodDaoController.getInstance();
    }

    public static CreditInfoDao getCreditInfoController() {
        return CreditInfoDaoController.getInstance();
    }

    public static MessengerActionDao getMessengerActionController() {
        return MessengerActionDaoController.getInstance();
    }

    public static CustomerBanHistoryDao getBanHistoryController() {
        return CustomerBanHistoryDaoController.getInstance();
    }

    public static StockDao getStockController() {
        return StockDaoController.getInstance();
    }

    public static DismissalHistoryDao getDismissalHistoryController() {
        return DismissalHistoryDaoController.getInstance();
    }

    public static DocumentDao getDocumentController() {
        return DocumentDaoController.getInstance();
    }

    public static MobileNumberDao getNumberMobileController() {
        return MobileNumberDaoController.getInstance();
    }

    public static QCRDao getQCRController() {
        return QCRDaoController.getInstance();
    }

    public static ProhibitionDao getProhibitionController() {
        return ProhibitionDaoController.getInstance();
    }

    public static RestrictionDao getRestrictionController() {
        return RestrictionDaoController.getInstance();
    }

    public static MessengerFBDao getMessengerFBController() {
        return MessengerFBDaoController.getInstance();
    }

    public static ShortcutDao getShortcutController() {
        return ShortcutDaoController.getInstance();
    }

    public static BaseDao getBaseController() {
        return BaseDaoController.getInstance();
    }

    public static CurriculumVitaeDao getCurriculumVitaeController() {
        return CurriculumVitaeDaoController.getInstance();
    }

    public static AbbreviationDao getAbbreviationController() {
        return AbbreviationDaoController.getInstance();
    }

    public static SpecialDayDao getSpecialDayController() {
        return SpecialDayDaoController.getInstance();
    }

    public static EvaluationDaoController getEvaluationController() {
        return EvaluationDaoController.getINSTANCE();
    }

    public static IssueDao getIssueController() {
        return IssueDaoController.getInstance();
    }

    public static PenaltyDao getPenaltyController() {
        return PenaltyDaoController.getInstance();
    }

    public static MarketingObsDao getMarketingObsController() {
        return MarketingObsDaoController.getInstance();
    }

    public static CashTallyingDao getCashTallyingController() {
        return CashTallyingDaoController.getInstance();
    }

    public static CashDao getCashController() {
        return CashDaoController.getInstance();
    }

    public static DailyOperationDao getDailyOperationController() {
        return DailyOperationDaoController.getInstance();
    }

    public static DomicileDao getDomicileController() {
        return DomicileDaoController.getInstance();
    }

    public static SecurityDepositDao getSecurityDepositController() {
        return SecurityDepositDaoController.getInstance();
    }

    public static DomicileReviewDao getDomicileReviewController() {
        return DomicileReviewDaoController.getInstance();
    }

    public static DomicileExecuteDao getDomicileExecuteController() {
        return DomicileExecuteDaoController.getInstance();
    }

    public static MarketingMessageDao getMarketingMessageController() {
        return MarketingMessageDaoController.getInstance();
    }

    public static GoodStandingDao getGoodStandingController() {
        return GoodStandingDaoController.getInstance();
    }

    public static FestiveDayDao getFestiveDayController() {
        return FestiveDayDaoController.getInstance();
    }

    public static SpecialDateDao getSpecialDateController() {
        return SpecialDateDaoController.getInstance();
    }

    public static MonitoringDao getMonitoringController() {
        return MonitoringDaoController.getInstance();
    }

    public static DiscountDao getDiscountController() {
        return DiscountDaoController.getInstance();
    }

    public static DiscountChangeLogDao getDiscountChangeLogController() {
        return DiscountChangeLogDaoController.getInstance();
    }

    public static AssignmentDao getAssignmentController() {
        return AssignmentDaoController.getInstance();
    }

    public static ShiftDao getShiftController() {
        return ShiftDaoController.getInstance();
    }

    public static ShiftplanningDao getShiftplanningController() {
        return ShiftplanningDaoController.getInstance();
    }

    public static ShiftAssignmentDao getShiftAssignmentController() {
        return ShiftAssignDaoController.getInstance();
    }

    public static ShiftCheckDao getShiftCheckController() {
        return ShiftCheckDaoController.getInstance();
    }

    public static MapPointDao getMapPointController() {
        return MapPointDaoController.getInstance();
    }

    public static PricingDao getPricingController() {
        return PricingDaoController.getInstance();
    }

    public static ScheduleDao getScheduleController() {
        return ScheduleDaoController.getInstance();
    }

    public static ShiftCheckKeyDao getShiftCheckKeyController() {
        return ShiftCheckKeyDaoController.getInstance();
    }

    public static EgressDao getEgressController() {
        return EgressDaoController.getInstance();
    }

    public static DebtDao getDebtController() {
        return DebtDaoController.getInstance();
    }

    public static PaymentDao getPaymentController() {
        return PaymentDaoController.getInstance();
    }

    public static MSettlementDao getMSettlementController() {
        return MSettlementDaoController.getInstance();
    }

    public static BirthdayDao getBirthdayController() {
        return BirthdayDaoController.getInstance();
    }

    public static ChipDao getChipController() {
        return ChipDaoController.getInstance();
    }

    public static WorkShiftPointDao getWorkShiftPointController() {
        return WorkShiftPointDaoController.getInstance();
    }

}
