import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RadnikComponent } from './list/radnik.component';
import { RadnikDetailComponent } from './detail/radnik-detail.component';
import { RadnikUpdateComponent } from './update/radnik-update.component';
import { RadnikDeleteDialogComponent } from './delete/radnik-delete-dialog.component';
import { RadnikRoutingModule } from './route/radnik-routing.module';

@NgModule({
  imports: [SharedModule, RadnikRoutingModule],
  declarations: [RadnikComponent, RadnikDetailComponent, RadnikUpdateComponent, RadnikDeleteDialogComponent],
})
export class RadnikModule {}
