library(WGCNA)
library(randomForest)
library(fuzzyforest)
library(ggplot2)
library(mvtnorm)

#set seed so that results are reproducible
set.seed(1711)

met_data <- read.csv("../met-data.csv", header = TRUE, sep = ",", stringsAsFactors = TRUE)
colnames(met_data)

num_of_cluster=6

clustered_temp <- kmeans(met_data[, 8], num_of_cluster, nstart = 20)

actual_pressure_cls = factor(clustered_temp$cluster)

actual_pressure_cls

met_data <- cbind(met_data, actual_pressure_cls)


training_size = 0.8

sample <- sample.int(n = nrow(met_data), size = floor(training_size*nrow(met_data)), replace = F)
train_data <- met_data[sample, ]
test_data  <- met_data[-sample, ]

#extract features and covariates from meterological data set
X_train <- subset(train_data, select = -c(actual_pressure, actual_pressure_cls))
X_target <- train_data$actual_pressure_cls
actual_pressure_clz <- X_target
actual_pressure_t <- train_data$actual_pressure

actual_pressure_t

dat <- as.data.frame(X_train) #as.data.frame(cbind(actual_pressure_t, X_train))

y_train <- subset(test_data, select = -c(actual_pressure, actual_pressure_cls))
y_target <- test_data$actual_pressure_cls
actual_pressure_clz <- y_target
actual_pressure_t <- test_data$actual_pressure

actual_pressure_t

y_dat <- as.data.frame(y_train) #as.data.frame(cbind(actual_pressure_t, y_train))


WGCNA_params <- WGCNA_control(p = 6, minModuleSize = 1, nThreads = 1)
mtry_factor <- 1; min_ntree <- 500; drop_fraction <- .5; ntree_factor <- 1

screen_params <- screen_control(drop_fraction = drop_fraction,
                                keep_fraction = .25, min_ntree = min_ntree,
                                ntree_factor = ntree_factor,
                                mtry_factor = mtry_factor)

select_params <- select_control(drop_fraction = drop_fraction,
                                number_selected = 5,
                                min_ntree = min_ntree,
                                ntree_factor = ntree_factor,
                                mtry_factor = mtry_factor)

library(WGCNA)
wff_fit <- wff(X_target ~ ., data=dat,
               WGCNA_params = WGCNA_params,
               screen_params = screen_params,
               select_params = select_params,
               final_ntree = 500)

#extract variable importance rankings
vims <- wff_fit$feature_list

#plot results
modplot(wff_fit)

preds <- predict(wff_fit, new_data=y_dat)

test_err <- sqrt(sum((as.numeric(y_target) - as.numeric(preds))^2)/length(preds))

test_err

100-test_err

library(caret)

predicted <- as.numeric(preds)
reference <- as.numeric(y_target)

predicted

reference

u <- union(predicted, reference)
t <- table(factor(predicted, u), factor(reference, u))
cm=confusionMatrix(t)

cm

#"Sensitivity", "Specificity", "Pos Pred Value", "Neg Pred Value", "Precision", "Recall", "F1", 
#"Prevalence", "Detection", "Rate", "Detection Prevalence", "Balanced Accuracy"

cm[["byClass"]][ , "Precision"]
cm[["byClass"]][ , "F1"]
cm[["byClass"]][ , "Recall"]

library(data.table)
library(mltools)
auc_roc(predicted, reference)

auc_roc(predicted, reference, returnDT=TRUE)


vims

wff_fit$module_membership

