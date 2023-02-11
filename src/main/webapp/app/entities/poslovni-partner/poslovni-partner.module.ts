import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PoslovniPartnerComponent } from './list/poslovni-partner.component';
import { PoslovniPartnerDetailComponent } from './detail/poslovni-partner-detail.component';
import { PoslovniPartnerUpdateComponent } from './update/poslovni-partner-update.component';
import { PoslovniPartnerDeleteDialogComponent } from './delete/poslovni-partner-delete-dialog.component';
import { PoslovniPartnerRoutingModule } from './route/poslovni-partner-routing.module';

@NgModule({
  imports: [SharedModule, PoslovniPartnerRoutingModule],
  declarations: [
    PoslovniPartnerComponent,
    PoslovniPartnerDetailComponent,
    PoslovniPartnerUpdateComponent,
    PoslovniPartnerDeleteDialogComponent,
  ],
})
export class PoslovniPartnerModule {}
