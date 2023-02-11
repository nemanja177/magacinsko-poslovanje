import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrometMagacinskeKarticeComponent } from './list/promet-magacinske-kartice.component';
import { PrometMagacinskeKarticeDetailComponent } from './detail/promet-magacinske-kartice-detail.component';
import { PrometMagacinskeKarticeUpdateComponent } from './update/promet-magacinske-kartice-update.component';
import { PrometMagacinskeKarticeDeleteDialogComponent } from './delete/promet-magacinske-kartice-delete-dialog.component';
import { PrometMagacinskeKarticeRoutingModule } from './route/promet-magacinske-kartice-routing.module';

@NgModule({
  imports: [SharedModule, PrometMagacinskeKarticeRoutingModule],
  declarations: [
    PrometMagacinskeKarticeComponent,
    PrometMagacinskeKarticeDetailComponent,
    PrometMagacinskeKarticeUpdateComponent,
    PrometMagacinskeKarticeDeleteDialogComponent,
  ],
})
export class PrometMagacinskeKarticeModule {}
